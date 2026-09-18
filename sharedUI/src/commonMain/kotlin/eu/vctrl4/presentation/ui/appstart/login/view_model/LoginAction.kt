package eu.vctrl4.presentation.ui.appstart.login.view_model

import eu.vctrl4.business.core.*

sealed class LoginAction: ViewSingleAction
{
    data object NavigateToMain: LoginAction()
    data object OfferUpdateApp: LoginAction()
    data object OnLoginSuccess: LoginAction()
}