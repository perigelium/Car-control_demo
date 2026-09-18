package eu.vctrl4.common

import kotlinx.coroutines.*
import org.jetbrains.compose.resources.*

val StringResource.asState: String
	get() = runBlocking {
		getString(this@asState)
	}

// definition: <string name="resource_name">all the orders by %1$s</string>
// call: Res.string.resource_name.asFormat(userName)
fun StringResource.asFormat(vararg formatArgs: Any): String = runBlocking {
	getString(this@asFormat, *formatArgs)
}

// For view models, call: Res.string.resource_name.load(userName)
suspend fun StringResource.load(vararg formatArgs: Any): String {
	return getString(this, *formatArgs)
}
