package eu.vctrl4.common

import android.content.Context
import eu.vctrl4.business.core.PrefsStoreManager
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.database.*
import eu.vctrl4.di.*
import org.koin.core.module.*
import org.koin.dsl.*

actual val platformModule: Module = module {
	single<AppDatabase> {
		// retrieves the context passed during Android startKoin
		val builder = getDatabaseBuilder(context = get<Context>())
		createDatabase(builder)
	}

    single<PrefsStoreManagerImpl> {
        PrefsStoreManagerImpl(context = get())
    } bind PrefsStoreManager::class
}