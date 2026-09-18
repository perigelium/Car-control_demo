package eu.vctrl4.common

import androidx.room.*
import eu.vctrl4.business.datasource.storage.database.*

expect fun getDatabaseBuilder(context: Any? = null): RoomDatabase.Builder<AppDatabase>