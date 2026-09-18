package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.requests.*
import eu.vctrl4.business.datasource.storage.entities.Session

class AuthUseCaseKtor(
	private val service: MainService,
	private val prefsStoreManager: PrefsStoreManagerImpl,
) : BaseUseCase<ApiRequestBodyKtor<AuthRequest>, Session?, Session?>() {

    override suspend fun callRepo(params: ApiRequestBodyKtor<AuthRequest>): MainGenericResponse<Session?>?
    {
        return service.auth(params)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<Session?>?): Session?
    {
        return apiResponse?.result
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}