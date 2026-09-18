package eu.vctrl4.common

import androidx.compose.runtime.*
import androidx.compose.ui.*
import platform.Foundation.*

@OptIn(ExperimentalComposeUiApi::class)
actual object LocalAppLocale {
	private const val LANG_KEY = "AppleLanguages"

	// Fall back to the device's primary system language if no manual choice is found
	private val default = NSLocale.preferredLanguages.first() as String
	private val LocalAppLocale = staticCompositionLocalOf { default }

	actual val current: String
		@Composable get() = LocalAppLocale.current

	@Composable
	actual infix fun provides(value: String?): ProvidedValue<*> {
		val newLanguage = value ?: default

		if (value == null) {
			// Revert back to the system locale if null is passed
			NSUserDefaults.standardUserDefaults.removeObjectForKey(LANG_KEY)
		} else {
			// Write the override directly into iOS user defaults
			// This forces the underlying resource qualifiers to pivot on runtime recomposition
			NSUserDefaults.standardUserDefaults.setObject(arrayListOf(newLanguage), LANG_KEY)
		}

		return LocalAppLocale provides newLanguage
	}
}

