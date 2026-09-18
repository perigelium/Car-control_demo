package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class SaveVehicleTypesToRoomUseCase (
    private val vehicleTypeDao: VehicleTypeDao
) : BaseRoomManagedUseCase<List<VehicleType>, List<VehicleType>, Unit>() {

    override val progressBarType = ProgressBarState.Idle
    override val showLoading = false

    override fun queryFlow(params: List<VehicleType>): Flow<List<VehicleType>?> = emptyFlow()

    override suspend fun querySingle(params: List<VehicleType>): List<VehicleType> {
        vehicleTypeDao.delAndUpsertAllVehicleTypes(params)
        return params
    }

    override fun convertEntity(entity: List<VehicleType>?) {
        return Unit
    }
}