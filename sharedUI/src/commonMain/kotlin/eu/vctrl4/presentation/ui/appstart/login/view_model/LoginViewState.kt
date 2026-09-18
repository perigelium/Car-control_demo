package eu.vctrl4.presentation.ui.appstart.login.view_model

import eu.vctrl4.business.core.*

data class LoginViewState(
    val strLogin: String? = "",
    val strPasswd: String? = "",
    val systemTimeSrv: String? = "",
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val fireBaseToken: String? = "",
) : ViewState