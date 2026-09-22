package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.requests.VehicleTrackRequest
import eu.vctrl4.business.datasource.storage.entities.*

class GetVehicleTrackUseCase(private val service: MainService): BaseUseCase<VehicleTrackRequest, List<VehicleTrackPoint>?, List<VehicleTrackPoint>?>()
{
    override suspend fun callRepo(params: VehicleTrackRequest): MainGenericResponse<List<VehicleTrackPoint>?>
    {
        return service.requestVehicleTrack(params)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<List<VehicleTrackPoint>?>?): List<VehicleTrackPoint>?
    {
        return apiResponse?.result
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}