package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.remote.entities.*

class RequestCompaniesUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl,
): BaseUseCase<UserIdObj, List<Company>?, List<Company>?>(prefsStoreManager)
{
	override suspend fun callRepo(params: UserIdObj): MainGenericResponse<List<Company>?>?
	{
		return service.requestCompanies(params)
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<Company>?>?): List<Company>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true
}