package business.core


interface PrefsStoreManager {

    suspend fun setValue(
        key: String,
        value: String
    )

    suspend fun readValue(
        key: String,
    ): String?

    suspend fun clearAllData()

}