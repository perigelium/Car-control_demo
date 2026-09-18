package eu.vctrl4

import androidx.compose.runtime.*
import androidx.compose.ui.window.*
import com.mmk.kmpnotifier.notification.*
import com.mmk.kmpnotifier.notification.configuration.*
import eu.vctrl4.presentation.*
import platform.UIKit.*

fun MainViewController(): UIViewController = ComposeUIViewController {

	NotifierManager.initialize(
		configuration = NotificationPlatformConfiguration.Ios(
			showPushNotification = true, askNotificationPermissionOnStart = false
		                                                     )
	                          )
	App()
}

@Composable
private fun ThemeChanged(isDark: Boolean) {
	LaunchedEffect(isDark) {
		UIApplication.sharedApplication.setStatusBarStyle(
			if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
		                                                 )
	}
}