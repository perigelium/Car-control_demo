package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class GetVehicleTypesFromRoomUseCase (
    private val vehicleTypeDao: VehicleTypeDao
) : BaseRoomManagedUseCase<String?, List<VehicleType>, List<VehicleType>>() {

    override val progressBarType = ProgressBarState.Loading

    override fun queryFlow(params: String?): Flow<List<VehicleType>?>
    {
        return vehicleTypeDao.getAllVehicleTypesAsFlow()
    }

    override suspend fun querySingle(params: String?): List<VehicleType> {
        return if(params==null) vehicleTypeDao.getAllVehicleTypesAsync() else vehicleTypeDao.getVehicleTypeByIdAsync(params)
    }

    override fun convertEntity(entity: List<VehicleType>?): List<VehicleType> {
        return entity ?: emptyList()
    }
}