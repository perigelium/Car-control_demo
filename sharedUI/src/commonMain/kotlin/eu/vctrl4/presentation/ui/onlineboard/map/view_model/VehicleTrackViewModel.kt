package eu.vctrl4.ui.online_board.map.view_model

import androidx.lifecycle.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.domain.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.storage.remote.entities.*
import kotlinx.coroutines.*

class VehicleTrackViewModel(
    private val getVehiclePositionUseCase: GetVehiclePositionUseCase,
    private val getVehicleTrackUseCase: GetVehicleTrackUseCase
) : BaseViewModel<VehicleTrackEvent, VehicleTrackViewState, Nothing>() {

    private var pollingJob: Job? = null

    override fun setInitialState(): VehicleTrackViewState = VehicleTrackViewState()

    override fun onTriggerEvent(event: VehicleTrackEvent) {
        when (event) {
            is VehicleTrackEvent.InitWithArgs -> handleInitArgs(event.order)
            is VehicleTrackEvent.ViewIsReady -> handleViewReady()
            else ->
            {
            }
        }
    }

    private fun handleInitArgs(order: BoardOrder?) {
        if (order == null) return

        val finished = calculateIsRentFinished(order)
        setState {
            copy(order = order, isRentFinished = finished)
        }
    }

    private fun handleViewReady() {
        val currentState = state.value

        if (currentState.isRentFinished) {
            fetchHistoricalTrack()
        } else {
            startPeriodicUpdates()
        }
    }

    private fun calculateIsRentFinished(order: BoardOrder): Boolean {
        order.RentEndDate?.let { endDateStr ->
            val rentEndDateTime = DateTimeUtils.toDateTime(endDateStr, "yyyy-MM-dd'T'HH:mm:ss", true)?:0
            val longRomeTime = DateTimeUtils.timeSofiaNowInMillis.toEpochMilliseconds()

            // 4 hours delay window preserved
            return longRomeTime >= (rentEndDateTime + 4 * Constants.HOUR_IN_MILLIS)
        }
        return false
    }

    private fun fetchHistoricalTrack() {
        val currentOrder = state.value.order ?: return

        val request = VehicleTrackRequest(
            currentOrder.NavDeviceId,
            currentOrder.WialonToken,
            currentOrder.RentStartDate,
            currentOrder.RentEndDate,
            currentOrder.WialonURL
        )

        executeUseCase(
            flow = getVehicleTrackUseCase.execute(request),
            onSuccess = { trackPoints ->
                val pointsList = mutableListOf<MapPoint>()

                trackPoints?.forEach { point ->
                    if (point.Y != null && point.X != null) {
                        pointsList.add(MapPoint(lat = point.Y, lng = point.X))
                    }
                }

                setState { copy(geoPoints = pointsList) }

                // Chain operation sequence: request live position right after history loads fetchSingleVehiclePosition()
            },
            onLoading = { progress ->
                setState { copy(progressBarState = progress) }
            }
        )
    }

    private fun fetchSingleVehiclePosition() {
        val currentOrder = state.value.order ?: return
        val request = VehiclePositionRequest(currentOrder.NavDeviceId, currentOrder.WialonToken, currentOrder.WialonURL)

        executeUseCase(
            flow = getVehiclePositionUseCase.execute(request),
            onSuccess = { mVehiclePosition ->
                if (mVehiclePosition?.X != null && mVehiclePosition.Y != null) {
                    val newPoint = MapPoint(lat = mVehiclePosition.Y, lng = mVehiclePosition.X)

                    setState {
                        copy(
                            vehiclePosition = mVehiclePosition,
                            geoPoints = geoPoints + newPoint,
                        )
                    }
                }
            },
            onLoading = { /* No-op single load */ }
        )
    }

    private fun startPeriodicUpdates() {
        pollingJob?.cancel() // Defend against duplicate timers
        pollingJob = viewModelScope.launch {
                fetchSingleVehiclePosition()
                delay(15000) // 15 seconds frequency loop
        }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel() // Cleans up polling when leaving screen context
    }
}