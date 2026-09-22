package eu.vctrl4.common

import eu.vctrl4.business.core.PrefsStoreManager
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.database.*
import eu.vctrl4.di.*
import org.koin.core.module.*
import org.koin.dsl.*

actual val platformModule: Module = module {
	single<AppDatabase> {
		// iOS does not require a Context argument to locate its storage path
		val builder = getDatabaseBuilder()
		createDatabase(builder)
	}

    single {
        PrefsStoreManagerImpl(context = null)
    } bind PrefsStoreManager::class
}