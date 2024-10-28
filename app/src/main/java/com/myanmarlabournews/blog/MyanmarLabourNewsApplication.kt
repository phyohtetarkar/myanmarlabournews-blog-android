package com.myanmarlabournews.blog

import android.app.Application
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.net.toUri
import com.myanmarlabournews.blog.ui.MainActivity
import com.onesignal.OneSignal
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyanmarLabourNewsApplication : Application() {

    private lateinit var _serviceLocator: ServiceLocator

    val serviceLocator: ServiceLocator
        get() = _serviceLocator

    private val notificationClickListener = object : INotificationClickListener {
        override fun onClick(event: INotificationClickEvent) {
            val data = event.notification.additionalData
            val context = this@MyanmarLabourNewsApplication

            if (data?.has("post_id") == true) {
                val deepLinkIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
                    context,
                    MainActivity::class.java
                )

                TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(deepLinkIntent)
                    .startActivities()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        _serviceLocator = DefaultServiceLocator(this)

        OneSignal.initWithContext(this, BuildConfig.ONE_SIGNAL_APP_ID)
        OneSignal.Location.isShared = false

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }

        OneSignal.Notifications.addClickListener(notificationClickListener)

//        OneSignal.setNotificationOpenedHandler { result ->
//            val data = result.notification.additionalData
//
//            if (data.has("post_id")) {
//                val deepLinkIntent = Intent(
//                    Intent.ACTION_VIEW,
//                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
//                    this,
//                    MainActivity::class.java
//                )
//
//                TaskStackBuilder.create(this)
//                    .addNextIntentWithParentStack(deepLinkIntent)
//                    .startActivities()
//
////                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
////                    addNextIntentWithParentStack(deepLinkIntent)
////                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
////                }
//            }
//        }
    }

}