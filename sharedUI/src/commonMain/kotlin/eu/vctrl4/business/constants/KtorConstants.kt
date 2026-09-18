package eu.vctrl4.business.constants

import kotlinx.serialization.json.*


val AppJson: Json = Json {
	explicitNulls = false
	ignoreUnknownKeys = true
	//isLenient = false         // Less strict syntax
	encodeDefaults = true    // Enable default values
	coerceInputValues = true
}
