package eu.vctrl4.business.usecase

import eu.vctrl4.business.constants.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.responses.*
import eu.vctrl4.storage.remote.entities.*

class DeleteFirebaseTokenUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl) :
    BaseUseCase<Unit, WDError?, WDError?>(prefsStoreManager)
{
    override suspend fun callRepo(params: Unit): MainGenericResponse<WDError?>?
    {
        val token = prefsStoreManager.readValue(PrefsStoreKeys.FIREBASE_TOKEN)

        return service.deleteFirebaseToken(TokenObj(token))
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<WDError?>?): WDError?
    {
        return apiResponse?.result
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Idle
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}