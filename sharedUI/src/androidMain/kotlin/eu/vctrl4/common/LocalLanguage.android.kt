package eu.vctrl4.common

import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import java.util.*

actual object LocalAppLocale {
	private var default: Locale? = null

	actual val current: String
		@Composable get() = Locale.getDefault().toString()

	@Composable
	actual infix fun provides(value: String?): ProvidedValue<*> {
		val configuration = LocalConfiguration.current
		val context = LocalContext.current

		if (default == null) {
			default = Locale.getDefault()
		}

		val newLocale = when (value) {
			null -> default!!
			else -> Locale(value)
		}

		Locale.setDefault(newLocale)
		configuration.setLocale(newLocale)

		val resources = context.resources
		resources.updateConfiguration(configuration, resources.displayMetrics)

		return LocalConfiguration provides configuration
	}
}
