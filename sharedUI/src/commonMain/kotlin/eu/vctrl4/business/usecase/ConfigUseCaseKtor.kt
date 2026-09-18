package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.responses.Config

class ConfigUseCaseKtor(
	private val service: MainService,
	private val prefsStoreManager: PrefsStoreManagerImpl,
) : BaseUseCase<Unit, List<Config?>?, Config?>(prefsStoreManager) {

    override suspend fun callRepo(params: Unit): MainGenericResponse<List<Config?>?>
    {
        return service.requestConfig()
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<List<Config?>?>?): Config?
    {
        if(apiResponse?.result?.isNotEmpty() == true)
        {
            return apiResponse.result?.get(0)
        }
        return null
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}