package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.*

class RequestVehicleCapacityClassesUseCase(private val service: MainService,
): BaseUseCase<Unit, List<VehicleCapacityClass>?, List<VehicleCapacityClass>?>()
{
	override suspend fun callRepo(params: Unit): MainGenericResponse<List<VehicleCapacityClass>?>
	{
		return service.requestVehicleCapacityClasses()
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<VehicleCapacityClass>?>?): List<VehicleCapacityClass>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true
}