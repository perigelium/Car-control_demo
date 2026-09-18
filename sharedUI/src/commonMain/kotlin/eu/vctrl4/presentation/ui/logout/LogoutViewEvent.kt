package eu.vctrl4.presentation.ui.logout

import eu.vctrl4.business.core.*

sealed class LogoutViewEvent: ViewEvent
{
    data class OnUpdateNetworkState(val networkState: NetworkState) : LogoutViewEvent()
    data object CleanUpUserSession : LogoutViewEvent()
}