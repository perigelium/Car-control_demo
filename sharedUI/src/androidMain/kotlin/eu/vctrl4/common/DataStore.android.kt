package eu.vctrl4.common

import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import eu.vctrl4.business.core.*
import kotlinx.coroutines.flow.*


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_DATASTORE)

actual suspend fun Context?.getData(key: String): String? {
    return this?.dataStore!!.data.first()[stringPreferencesKey(key)]
}

actual suspend fun Context?.putData(key: String, `object`: String) {
    this?.dataStore!!.edit {
        it[stringPreferencesKey(key)] = `object`
    }
}

actual suspend fun Context?.clearAllData() {
	this?.dataStore!!.edit { preferences ->
		preferences.clear()
	}
}

