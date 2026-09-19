package eu.vctrl4

import androidx.compose.runtime.*
import androidx.compose.ui.window.*
import eu.vctrl4.presentation.*
import platform.UIKit.*

fun MainViewController(): UIViewController = ComposeUIViewController {
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
