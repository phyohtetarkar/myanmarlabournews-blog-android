package com.myanmarlabournews.blog

import android.app.Application
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.net.toUri
import com.myanmarlabournews.blog.ui.MainActivity
import com.onesignal.OneSignal

const val ONE_SIGNAL_APP_ID = "c6cb7884-f54d-4585-933d-f61955cf17c6"

class MyanmarLabourNewsApplication : Application() {

    private lateinit var _serviceLocator: ServiceLocator

    val serviceLocator: ServiceLocator
        get() = _serviceLocator

    override fun onCreate() {
        super.onCreate()

        _serviceLocator = DefaultServiceLocator(this)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONE_SIGNAL_APP_ID)
        OneSignal.setLocationShared(false)

        OneSignal.promptForPushNotifications()

        OneSignal.setNotificationOpenedHandler { result ->
            val data = result.notification.additionalData

            if (data.has("post_id")) {
                val deepLinkIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
                    this,
                    MainActivity::class.java
                )

                TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(deepLinkIntent)
                    .startActivities();

//                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
//                    addNextIntentWithParentStack(deepLinkIntent)
//                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//                }
            }
        }
    }

}