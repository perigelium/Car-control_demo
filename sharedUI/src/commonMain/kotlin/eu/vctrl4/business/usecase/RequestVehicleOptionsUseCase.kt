package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.*

class RequestVehicleOptionsUseCase(private val service: MainService,
): BaseUseCase<Unit, List<VehicleOption>?, List<VehicleOption>?>()
{
	override suspend fun callRepo(params: Unit): MainGenericResponse<List<VehicleOption>?>
	{
		return service.requestVehicleOptions()
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<VehicleOption>?>?): List<VehicleOption>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true
}