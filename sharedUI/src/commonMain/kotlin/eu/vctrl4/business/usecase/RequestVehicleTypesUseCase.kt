package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.*

class RequestVehicleTypesUseCase(private val service: MainService,
): BaseUseCase<Unit, List<VehicleType>?, List<VehicleType>?>()
{
	override suspend fun callRepo(params: Unit): MainGenericResponse<List<VehicleType>?>
	{
		return service.requestVehicleTypes()
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<VehicleType>?>?): List<VehicleType>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true
}