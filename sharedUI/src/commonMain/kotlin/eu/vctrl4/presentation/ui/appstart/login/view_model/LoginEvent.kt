package eu.vctrl4.presentation.ui.appstart.login.view_model

import eu.vctrl4.business.core.*


sealed class LoginEvent : ViewEvent {
	data class OnUpdateLoginPassword(val login: String, val password: String) : LoginEvent()

	data class Login(val login: String?, val password: String?) : LoginEvent()

	data class OnLanguageCodeChanged(val languageCode: String) : LoginEvent()

	data class OnUpdateNetworkState(val networkState: NetworkState) : LoginEvent()
}
