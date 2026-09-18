package eu.vctrl4.common

import androidx.room.*
import eu.vctrl4.business.datasource.storage.database.*
import kotlinx.cinterop.*
import platform.Foundation.*

/*@OptIn(ExperimentalForeignApi::class)
actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase> {
	val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
		directory = NSDocumentDirectory,
		inDomain = NSUserDomainMask,
		appropriateForURL = null,
		create = true,
		error = null
	                                                                    )
	val dbFile = documentDirectory?.path + "/app_db.db"
	return Room.databaseBuilder<AppDatabase>(name = dbFile)
}*/

@OptIn(ExperimentalForeignApi::class)
actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase> {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null
    )
    val dbFile = requireNotNull(documentDirectory?.path) + "/app_db.db"

    // Clear out the factory parameter entirely.
    // Room relies on the AppDatabaseConstructor interface now.
    return Room.databaseBuilder<AppDatabase>(name = dbFile)
        .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
}