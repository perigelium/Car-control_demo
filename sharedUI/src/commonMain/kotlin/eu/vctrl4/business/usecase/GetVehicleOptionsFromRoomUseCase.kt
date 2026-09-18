package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*
import kotlinx.coroutines.flow.*

class GetVehicleOptionsFromRoomUseCase (
    private val vehicleOptionDao: VehicleOptionDao
) : BaseRoomManagedUseCase<String?, List<VehicleOption>, List<VehicleOption>>() {

    override val progressBarType = ProgressBarState.Loading

    override fun queryFlow(params: String?): Flow<List<VehicleOption>?>
    {
        return vehicleOptionDao.getAllVehicleOptionsAsFlow()
    }

    override suspend fun querySingle(params: String?): List<VehicleOption> {
        return if(params==null) vehicleOptionDao.getAllVehicleOptionsAsync() else vehicleOptionDao.getVehicleOptionByIdAsync(params)
    }

    override fun convertEntity(entity: List<VehicleOption>?): List<VehicleOption> {
        return entity ?: emptyList()
    }
}