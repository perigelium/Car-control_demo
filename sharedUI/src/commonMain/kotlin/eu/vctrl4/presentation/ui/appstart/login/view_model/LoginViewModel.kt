package eu.vctrl4.presentation.ui.appstart.login.view_model


import androidx.lifecycle.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogTitleText
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlAuth
import eu.vctrl4.business.datasource.network.main.requests.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.utils.*
import kotlinx.coroutines.*

class LoginViewModel(
	private val configUseCaseKtor: ConfigUseCaseKtor,
	private val authUseCaseKtor: AuthUseCaseKtor,
	private val requestCompaniesUseCase: RequestCompaniesUseCase,
	private val saveCompaniesToRoomUseCase: SaveCompaniesToRoomUseCase,
	private val getCompaniesFromRoomUseCase: GetCompaniesFromRoomUseCase,
	private val prefsStoreManager: PrefsStoreManagerImpl
                    ) : BaseViewModel<LoginEvent, LoginViewState, LoginAction>() {
	override fun setInitialState(): LoginViewState {
		return LoginViewState()
	}

	init {
		viewModelScope.launch {
			val strLogin = prefsStoreManager.readValue(PrefsStoreKeys.PREF_LOGIN)
			val strPasswd = prefsStoreManager.readValue(PrefsStoreKeys.PREF_PASSWORD)
			val firebaseToken = prefsStoreManager.readValue(PrefsStoreKeys.FIREBASE_TOKEN)
			setState {
				copy(
					fireBaseToken = firebaseToken,
					strLogin = strLogin,
					strPasswd = strPasswd
				    )
			}
		}
	}

	override fun onTriggerEvent(event: LoginEvent) {
		when (event) {

			is LoginEvent.OnLanguageCodeChanged ->
			{
				saveLanguageCode(event.languageCode)
			}

			is LoginEvent.OnUpdateNetworkState -> {
				if (event.networkState == NetworkState.Failed) display { Toast(Res.string.error_check_internet_connection.asState) }
			}

			is LoginEvent.Login -> {
				setState { copy(strLogin = event.login, strPasswd = event.password) }
				getConfig()
			}

			is LoginEvent.OnUpdateLoginPassword -> {
				setState { copy(strLogin = strLogin, strPasswd = strPasswd) }

			}
		}
	}

	fun saveLanguageCode(languageCode: String) {

		changeAppLanguage(languageCode)

		viewModelScope.launch(Dispatchers.IO) {
			prefsStoreManager.setValue(PrefsStoreKeys.CURRENT_LANGUAGE, languageCode)
		}
	}

	private fun getConfig() {
		executeUseCase(configUseCaseKtor.execute(Unit), onSuccess = { config ->
			if (config != null) {
				config.SystemTime?.let {
					setState { copy(systemTimeSrv = it) }
				}

				config.AppVersion?.Android?.let {

					val digitsVerLocal = BuildKonfig.VERSION_CODE
					if (it.all { it.isDigit() }) {
						if (it.toInt() > digitsVerLocal) {
							setAction { LoginAction.OfferUpdateApp }
						} else {
							login()
						}
					}
				}
			} else {
				display {
					DialogTitleText(
						Res.string.error.asState,
						Res.string.error_login_failed.asState
					               )
				}
			}
		}, onLoading = {
			setState { copy(progressBarState = it) }
		}, onNetworkStatus = {
			setEvent(LoginEvent.OnUpdateNetworkState(it))
		})
	}

	private fun login() {
		if (state.value.strLogin.isNullOrBlank() || state.value.strPasswd.isNullOrBlank()) {
			display { Toast(Res.string.error_fill_login_and_password.asState) }
			return
		}
		val apiRequestBodyKtor = prepareAuthRequest()

		apiRequestBodyKtor?.let {
			executeUseCase(
				authUseCaseKtor.execute(
				it
			                                      ), onSuccess = { session ->

				if (session != null) {
					SessionVars.userSession = session
					SessionVars.isAutoLogin = false

					viewModelScope.launch {

						state.value.strLogin?.let {
							prefsStoreManager.setValue(PrefsStoreKeys.PREF_LOGIN, it)
						}
						state.value.strPasswd?.let {
							prefsStoreManager.setValue(PrefsStoreKeys.PREF_PASSWORD, it)
						}
					}

					if (BuildKonfig.DEBUG) {
						display {
							SessionVars.userSession.ProjectCode?.let { message ->
								Toast(
									message
								     )
							}!!
						}
					}

					requestAndSaveCompanies()

				} else {
					display {
						DialogTitleText(
							Res.string.error.asState,
							Res.string.error_login_failed.asState
						               )
					}
				}
			}, onLoading = {
				setState { copy(progressBarState = it) }
			}, onNetworkStatus = {
				setEvent(LoginEvent.OnUpdateNetworkState(it))
			})
		}
	}

	private fun prepareAuthRequest(): ApiRequestBodyKtor<AuthRequest>? {
		var apiRequestBody: ApiRequestBodyKtor<AuthRequest>? = null

		//var firebaseToken: String? = state.value.fireBaseToken

		/*        FirebaseMessaging.getInstance().token.addOnCompleteListener { task: Task<String> ->

					if (!task.isSuccessful)
					{
						if(BuildKonfig.IS_DEBUG)
						{
							display { Toast("Fetching FCM registration token failed") }
						}
						return@addOnCompleteListener
					}
					firebaseToken = task.result
				}*/

		val hDate = DateTimeUtils.reformatDateTime(
			state.value.systemTimeSrv,
			DateTimeUtils.SERVER_DATE_TIME_PATTERN_LONG,
			DateTimeUtils.SERVER_DATE_PATTERN
		                                          )
		val hUserName = (state.value.strLogin)?.toMd5Hex() ?: ""
		val hPassword = (state.value.strPasswd?.toMd5Hex() + hDate + Constants.API_KEY).toMd5Hex()
		val type = urlAuth
		val authRequestBody = AuthRequest(hUserName, hPassword, true, state.value.fireBaseToken)
		apiRequestBody = ApiClientKtor.buildApiRequestBody(type, authRequestBody)

		return apiRequestBody
	}

	private fun requestAndSaveCompanies() {
		executeUseCase(
			requestCompaniesUseCase.execute(params = UserIdObj()),
			onSuccess = { companies ->
				if (companies?.isNotEmpty() == true) {
					val actualCompanies: List<Company> = companies.filter { it.IsDeleted != true }

					executeUseCaseLocal(
						saveCompaniesToRoomUseCase.execute(
						params = actualCompanies,
						isFlow = false
					                                                      ),
					                    onSuccess = {},
					                    onLoading = { setState { copy(progressBarState = it) } })
				} else {
					display { UIComponent.DialogMsg(JAlertResponse(Res.string.error_load_companies.asState)) }
				}

				/*	        executeUseCaseLocal(
								getCompaniesFromRoomUseCase.execute(params = Unit, isFlow = false),
								onSuccess = { allCompanies ->

									val supplierCompanies: MutableList<Company> =
										allCompanies?.filter { it.IsSupplier == true } as MutableList<Company>

								}, onLoading = { setState { copy(progressBarState = it) } }
											   )*/

				setAction { LoginAction.OnLoginSuccess }


			},
			onLoading = {
				setState { copy(progressBarState = it) }
			},
			onNetworkStatus = {
				setEvent(LoginEvent.OnUpdateNetworkState(it))
			})
	}
}