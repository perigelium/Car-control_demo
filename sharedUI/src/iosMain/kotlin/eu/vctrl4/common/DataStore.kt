package eu.vctrl4.common

import kotlinx.coroutines.flow.*
import platform.Foundation.*


actual suspend fun Context?.putData(key: String, `object`: String) {
    val sharedFlow = MutableSharedFlow<String>()
    NSUserDefaults.standardUserDefaults().setObject(`object`, key)
    sharedFlow.emit(`object`)
}

actual suspend inline fun Context?.getData(key: String): String? {
    return NSUserDefaults.standardUserDefaults().stringForKey(key)
}

actual suspend fun Context?.clearAllData() {
	// clear
}
