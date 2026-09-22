package eu.vctrl4.business.core

import eu.vctrl4.common.*

const val APP_DATASTORE = "eu.vctrl4"

class PrefsStoreManagerImpl(val context: Context?) : PrefsStoreManager {

    override suspend fun setValue(
        key: String,
        value: String
    ) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }

    override suspend fun clearAllData()
    {
        return context.clearAllData()
    }
}