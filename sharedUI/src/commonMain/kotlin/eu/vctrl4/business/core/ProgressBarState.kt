package eu.vctrl4.business.core

sealed class ProgressBarState{

   //data object ButtonLoading: ProgressBarState()

   //data object ScreenLoading: ProgressBarState()

   data object Loading: ProgressBarState()

   //data object LoadingWithLogo: ProgressBarState()

   data object Idle: ProgressBarState()

}

