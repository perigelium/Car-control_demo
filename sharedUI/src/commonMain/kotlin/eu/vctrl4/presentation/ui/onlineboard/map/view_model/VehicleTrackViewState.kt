package eu.vctrl4.presentation.ui.onlineboard.map.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.domain.*
import eu.vctrl4.storage.remote.entities.*

data class VehicleTrackViewState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val order: BoardOrder? = null,
    val isRentFinished: Boolean = false,
    val vehiclePosition: VehiclePosition? = null,
    val geoPoints: List<MapPoint> = emptyList(),

    val orderNumberText: String = "No data",
    val vehicleNumberText: String = "No data",
    val rentStartedText: String = "No data",
    val rentFinishedText: String = "No data",
    val driverFioText: String = "No data",
    val driverPhoneText: String = "No data",
    val mileageFinalText: String = "No data",
    val rentTimeSignedText: String = "No data",

) : ViewState