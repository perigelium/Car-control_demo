package eu.vctrl4.presentation.ui.logout

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogMsg
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import kotlinx.coroutines.*

class LogoutViewModel(private val deleteTokenUseCase: DeleteFirebaseTokenUseCase, private val prefsStoreManager: PrefsStoreManagerImpl
)
    : BaseViewModel<LogoutViewEvent, LogoutViewState, LogoutViewAction>()
{
    override fun setInitialState() = LogoutViewState()

    override fun onTriggerEvent(event: LogoutViewEvent)
    {
        when (event)
        {
            is LogoutViewEvent.OnUpdateNetworkState -> {
                if(event.networkState == NetworkState.Failed) display { UIComponent.Toast(Res.string.error_check_internet_connection.asState) }
            }

            is LogoutViewEvent.CleanUpUserSession ->
            {
                runBlocking {
                    prefsStoreManager.clearAllData()
                    SessionVars.userSession = Session()
                }
                requestDeleteFirebaseToken()
	            setAction { LogoutViewAction.CleanUpUserSessionCompleted }
            }
        }
    }

    private fun requestDeleteFirebaseToken()
    {
        executeUseCase(deleteTokenUseCase.execute(params = Unit),
            onSuccess = { resp ->
                if(BuildKonfig.DEBUG)
                {
                    if (resp != null)
                    {
                        if (resp.code == 0)
                        {
                            display { UIComponent.Toast("Firebase token request deletion succeeded") }
                        } else if (resp.msg?.isNotBlank() == true)
                        {
                            display { DialogMsg(JAlertResponse("Error !", resp.msg!!)) }
                        }
                    } else
                    {
                        display { UIComponent.Toast("Firebase token request deletion failed") }
                    }
                }
                SessionVars.isAutoLogin = false
                setAction { LogoutViewAction.CleanUpUserSessionCompleted }
            }, onLoading = {
                setState { copy(progressBarState = it) }
            }, onNetworkStatus = {
                setEvent(LogoutViewEvent.OnUpdateNetworkState(it))
            })
    }
}