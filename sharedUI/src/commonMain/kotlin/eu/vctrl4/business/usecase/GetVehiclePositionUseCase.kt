package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.storage.remote.entities.*

class GetVehiclePositionUseCase(private val service: MainService):
    BaseUseCase<VehiclePositionRequest, VehiclePosition?, VehiclePosition?>()
{
    override suspend fun callRepo(params: VehiclePositionRequest): MainGenericResponse<VehiclePosition?>
    {
        return service.requestVehiclePosition(params)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<VehiclePosition?>?): VehiclePosition?
    {
        return apiResponse?.result
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}