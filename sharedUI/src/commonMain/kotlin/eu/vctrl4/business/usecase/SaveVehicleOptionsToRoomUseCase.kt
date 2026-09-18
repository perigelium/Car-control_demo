package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class SaveVehicleOptionsToRoomUseCase (
    private val vehicleOptionTypeDao: VehicleOptionDao
) : BaseRoomManagedUseCase<List<VehicleOption>, List<VehicleOption>, Unit>() {

    override val progressBarType = ProgressBarState.Idle
    override val showLoading = false

    override fun queryFlow(params: List<VehicleOption>): Flow<List<VehicleOption>?> = emptyFlow()

    override suspend fun querySingle(params: List<VehicleOption>): List<VehicleOption> {
        vehicleOptionTypeDao.delAndUpsertAllVehicleOptions(params)
        return params
    }

    override fun convertEntity(entity: List<VehicleOption>?) {
        return Unit
    }
}