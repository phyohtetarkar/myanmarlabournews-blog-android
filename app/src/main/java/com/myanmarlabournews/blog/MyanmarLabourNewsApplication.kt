package com.myanmarlabournews.blog

import android.app.Application
import com.onesignal.OneSignal

class MyanmarLabourNewsApplication : Application() {

    private lateinit var _serviceLocator: ServiceLocator

    val serviceLocator: ServiceLocator
        get() = _serviceLocator

    override fun onCreate() {
        super.onCreate()

        _serviceLocator = DefaultServiceLocator(this)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.ONE_SIGNAL_APP_ID)
        OneSignal.setLocationShared(false)

        OneSignal.promptForPushNotifications()

        OneSignal.setNotificationOpenedHandler { result ->
            val data = result.notification.additionalData
        }
    }

}