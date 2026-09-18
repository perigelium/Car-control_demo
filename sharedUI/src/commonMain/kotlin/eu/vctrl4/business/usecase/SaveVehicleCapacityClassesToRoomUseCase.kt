package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class SaveVehicleCapacityClassesToRoomUseCase (
    private val vehicleCapacityClassDao: VehicleCapacityClassDao
) : BaseRoomManagedUseCase<List<VehicleCapacityClass>, List<VehicleCapacityClass>, Unit>() {

    override val progressBarType = ProgressBarState.Idle
    override val showLoading = false

    override fun queryFlow(params: List<VehicleCapacityClass>): Flow<List<VehicleCapacityClass>?> = emptyFlow()

    override suspend fun querySingle(params: List<VehicleCapacityClass>): List<VehicleCapacityClass> {
        vehicleCapacityClassDao.delAndUpsertAllVehicleCapacityClasses(params)
        return params
    }

    override fun convertEntity(entity: List<VehicleCapacityClass>?) {
        return Unit
    }
}