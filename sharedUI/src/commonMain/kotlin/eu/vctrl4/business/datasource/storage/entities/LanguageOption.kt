package eu.vctrl4.business.datasource.storage.entities

data class LanguageOption(
	val code: String,
	val displayName: String,
	val flagEmoji: String
                         )

val supportedLanguages = listOf(
    LanguageOption(code = "it", displayName = "Italiano", flagEmoji = "🇮🇹"),
	LanguageOption(code = "en", displayName = "English", flagEmoji = "🇺🇸"),
    LanguageOption(code = "bg", displayName = "Български", flagEmoji = "🇧🇬"),
	LanguageOption(code = "de", displayName = "Deutsch", flagEmoji = "🇩🇪"),

                               )
