package eu.vctrl4.common

expect suspend fun Context?.putData(key: String, `object`: String)

expect suspend fun Context?.getData(key: String): String?

expect suspend fun Context?.clearAllData()


