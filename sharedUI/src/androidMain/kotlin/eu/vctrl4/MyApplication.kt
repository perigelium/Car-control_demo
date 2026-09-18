package eu.vctrl4

import android.app.*
import com.mmk.kmpnotifier.notification.*
import com.mmk.kmpnotifier.notification.configuration.*
import eu.vctrl4.di.*
import org.koin.android.ext.koin.*

class MyApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		NotifierManager.initialize(
			configuration = NotificationPlatformConfiguration.Android(
				notificationIconResId = R.drawable.ic_gear_small,
				showPushNotification = true)
		                          )

		initKoin {
			androidContext(this@MyApplication)
		}
	}
}