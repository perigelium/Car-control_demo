package eu.vctrl4.common

import androidx.room.*
import eu.vctrl4.business.datasource.storage.database.*

actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase> {
	val appContext = (context as Context).applicationContext
	val dbFile = appContext.getDatabasePath("app_db.db")
	return Room.databaseBuilder<AppDatabase>(
		context = appContext,
		name = dbFile.absolutePath
	                                        )
}