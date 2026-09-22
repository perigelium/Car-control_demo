package eu.vctrl4.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.core.PrefsStoreManager
import eu.vctrl4.common.*
import eu.vctrl4.presentation.navigation.*
import eu.vctrl4.presentation.ui.*
import eu.vctrl4.presentation.ui.appstart.*
import eu.vctrl4.presentation.ui.appstart.login.*
import eu.vctrl4.presentation.ui.appstart.login.view_model.*
import eu.vctrl4.presentation.ui.base.*
import eu.vctrl4.theme.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.compose.*
import org.koin.compose.viewmodel.*

@Composable
internal fun App() {

	val prefsStoreManager: PrefsStoreManager = koinInject()

	val languageCode = runBlocking(Dispatchers.IO) {
		prefsStoreManager.readValue(PrefsStoreKeys.CURRENT_LANGUAGE) ?: "en"
	}

	changeAppLanguage(languageCode)

	AppEnvironment {
		AppTheme() {

			val navigator = rememberNavController()
			val viewModel: LoginViewModel = koinViewModel()
			val scope = rememberCoroutineScope()

			fun next() {
				navigator.navigate(AppStartNavigation.Main)
			}

			fun calculateStartDestination(): String {
				val chatIdFromPush: String? = SessionVars.fromPushVariables.chatId

				val startDestination = when {
					!chatIdFromPush.isNullOrEmpty() -> "nav_chats"
					else -> "nav_orders"
				}
				return startDestination
			}

			LaunchedEffect(Unit) {
				delay(500L)
				viewModel.action.onEach { effect ->
					when (effect) {
						LoginAction.NavigateToMain -> {
							next()
						}

						LoginAction.OfferUpdateApp -> {
							navigator.navigate(AppStartNavigation.OfferUpdateApplication)
						}

						LoginAction.OnLoginSuccess -> {
							next()
						}
					}
				}.collect {}
			}

			NavHost(
				startDestination = AppStartNavigation.Welcome,
				navController = navigator,
				modifier = Modifier.fillMaxSize()
			       ) {

				composable<AppStartNavigation.Welcome> {

					WelcomeScreen(onLanguageChanged = {
						viewModel.onTriggerEvent(LoginEvent.OnLanguageCodeChanged(it))
					}, onSubmit = {
						navigator.navigate(AppStartNavigation.Login)
					})
				}

				composable<AppStartNavigation.Login> {
					DefaultScreenWrap(
						errors = viewModel.errors,
						progressBarState = viewModel.state.value.progressBarState,
						screenContent = {
							LoginScreen(
								enableSubmitBtn = true,
								resetFields = false,
								onSubmit = {
									viewModel.onTriggerEvent(
										LoginEvent.Login(
											it.first, it.second
										                )
									                        )
								},
								onPrivacyPolicyClicked = {
								// showPrivacyPolicy()
								},
								state = viewModel.state.value,
							           )
						})
				}

				composable<AppStartNavigation.Main> {
					val navController = rememberNavController()

					Surface(
						modifier = Modifier.fillMaxSize().safeDrawingPadding()
					       ) {

						MainNavDrawer(
							navController = navController,
							title = SessionVars.userSession.Profile?.UserName ?: "",
							screenContent = { drawerState ->
								MainNav(
									navController,
									startDestination = calculateStartDestination(),
									drawerState = drawerState,
									onCleanupUserSessionCompleted = {
										navigator.popBackStack(
											route = AppStartNavigation.Login, false
										                      )
									})
							},
						             )
					}
				}

				composable<AppStartNavigation.OfferUpdateApplication> {
					TitleTextInfoDialog(
						dialogTitle = Res.string.app_update_required.asState,
						strMsg = "",
						Res.string.refresh.asState,
						{                        //startUpdateApplication()
						})
				}
			}
		}
	}
}




