package eu.vctrl4.presentation.ui.logout

import eu.vctrl4.business.core.*

sealed class LogoutViewAction: ViewSingleAction
{
    data object CleanUpUserSessionCompleted: LogoutViewAction()
}