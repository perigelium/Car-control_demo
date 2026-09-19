package eu.vctrl4

import android.app.*
import eu.vctrl4.di.*
import org.koin.android.ext.koin.*

class MyApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		initKoin {
			androidContext(this@MyApplication)
		}
	}
}