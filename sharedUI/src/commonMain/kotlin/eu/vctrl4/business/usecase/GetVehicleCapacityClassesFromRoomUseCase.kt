package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class GetVehicleCapacityClassesFromRoomUseCase (
    private val vehicleCapacityClassDao: VehicleCapacityClassDao
) : BaseRoomManagedUseCase<String?, List<VehicleCapacityClass>, List<VehicleCapacityClass>>() {

    override val progressBarType = ProgressBarState.Loading

    override fun queryFlow(params: String?): Flow<List<VehicleCapacityClass>?>
    {
        return vehicleCapacityClassDao.getAllVehicleCapacityClassesAsFlow()
    }

    override suspend fun querySingle(params: String?): List<VehicleCapacityClass> {
        return if(params==null) vehicleCapacityClassDao.getAllVehicleCapacityClassesAsync() else vehicleCapacityClassDao.getVehicleCapacityClassByIdAsync(params)
    }

    override fun convertEntity(entity: List<VehicleCapacityClass>?): List<VehicleCapacityClass> {
        return entity ?: emptyList()
    }
}