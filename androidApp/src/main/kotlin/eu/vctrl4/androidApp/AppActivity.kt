package eu.vctrl4.androidApp

import MainView
import android.app.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.core.view.*

class AppActivity : ComponentActivity() {

/*		override fun attachBaseContext(newBase: android.content.Context) {

			val locale = Locale("bg")
			Locale.setDefault(locale)

			val configuration = Configuration(newBase.resources.configuration)
			configuration.setLocale(locale)

			val localizedContext = newBase.createConfigurationContext(configuration)
			super.attachBaseContext(localizedContext)
		}*/

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

/*		val appLocales = LocaleListCompat.forLanguageTags("bg")
		AppCompatDelegate.setApplicationLocales(appLocales)*/

		setContent {
			MainView()
		}
	}
}

@Composable
private fun ThemeChanged(isDark: Boolean) {
	val view = LocalView.current
	LaunchedEffect(isDark) {
		val window = (view.context as Activity).window
		WindowInsetsControllerCompat(window, window.decorView).apply {
			isAppearanceLightStatusBars = isDark
			isAppearanceLightNavigationBars = isDark
		}
	}
}
