package eu.vctrl4.common

import androidx.compose.runtime.*

fun changeAppLanguage(languageCode: String) {
	customAppLocale = languageCode
}

var customAppLocale by mutableStateOf<String?>(null)

expect object LocalAppLocale {
	val current: String
		@Composable get

	@Composable
	infix fun provides(value: String?): ProvidedValue<*>
}

@Composable
fun AppEnvironment(content: @Composable () -> Unit) {
	CompositionLocalProvider(
		LocalAppLocale provides customAppLocale,
	                        ) {
		key(customAppLocale) {
			content()
		}
	}
}

