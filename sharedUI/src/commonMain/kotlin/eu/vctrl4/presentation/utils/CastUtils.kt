package eu.vctrl4.presentation.utils

import io.ktor.utils.io.core.*
import korlibs.crypto.*
import kotlinx.serialization.json.*

object CastUtils {

	fun <T, E> getKeyByValue(map: Map<T, E>, value: E): T? {
		for ((key, value1) in map) {
			if (value == value1) {
				return key
			}
		}
		return null
	}
}

fun JsonObject.toMapStringAny(): Map<String, Any?> = this.mapValues { (_, value) -> value.toAny() }

private fun JsonElement.toAny(): Any? = when (this) {
	is JsonNull -> null
	is JsonObject -> this.toMap()
	is JsonArray -> this.map { it.toAny() }
	is JsonPrimitive -> {
		if (this.isString) {
			this.content
		} else {			// Automatically capture Boolean, Int, Long, or Double
			this.booleanOrNull ?: this.longOrNull ?: this.doubleOrNull ?: this.content
		}
	}
}

fun String.toMd5Hex(): String {
	return MD5.digest(this.toByteArray()).hex
}