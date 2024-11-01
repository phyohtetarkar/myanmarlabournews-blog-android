package com.myanmarlabournews.blog.util

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.Keep
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.myanmarlabournews.blog.ui.MainActivity
import com.onesignal.notifications.INotificationReceivedEvent
import com.onesignal.notifications.INotificationServiceExtension
import kotlin.random.Random
import kotlin.random.nextInt

@Keep
class NotificationServiceExtension : INotificationServiceExtension {
//    override fun remoteNotificationReceived(
//        context: Context,
//        notificationReceivedEvent: OSNotificationReceivedEvent
//    ) {
//        val notification = notificationReceivedEvent.notification
//
//        val mutableNotification = notification.mutableCopy()
//
//        val data = notification.additionalData
//
////        Log.i("OneSignalExample", "Received Notification Data: $data")
//
//        mutableNotification.setExtender { builder ->
//            if (data.has("post_id")) {
//                val requestCode = Random.nextInt(1 until 99999)
//                val deepLinkIntent = Intent(
//                    Intent.ACTION_VIEW,
//                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
//                    context,
//                    MainActivity::class.java
//                )
//
//                var pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                }
//
//                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
//                    addNextIntentWithParentStack(deepLinkIntent)
//                    getPendingIntent(requestCode, pendingFlags)
//                }
//
//                builder.setContentIntent(deepLinkPendingIntent)
//            }
//            return@setExtender builder
//        }
//
//        notificationReceivedEvent.complete(mutableNotification)
//    }

    override fun onNotificationReceived(event: INotificationReceivedEvent) {
        val context = event.context
        val notification = event.notification

        val data = notification.additionalData

//        Log.i("OneSignalExample", "Received Notification Data: $data")

        notification.setExtender { builder ->
            if (data?.has("post_id") == true) {
                val requestCode = Random.nextInt(1 until 99999)
                val deepLinkIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
                    context,
                    MainActivity::class.java
                )

                var pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                }

                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(deepLinkIntent)
                    getPendingIntent(requestCode, pendingFlags)
                }

                builder.setContentIntent(deepLinkPendingIntent)
            }
            return@setExtender builder
        }
    }
}