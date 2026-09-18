package eu.vctrl4.presentation.ui.logout

import eu.vctrl4.business.core.*

data class LogoutViewState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState