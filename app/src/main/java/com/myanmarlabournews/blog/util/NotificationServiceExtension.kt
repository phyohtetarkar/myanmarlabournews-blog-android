package com.myanmarlabournews.blog.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.myanmarlabournews.blog.ui.MainActivity
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler

class NotificationServiceExtension : OSRemoteNotificationReceivedHandler {
    override fun remoteNotificationReceived(
        context: Context,
        notificationReceivedEvent: OSNotificationReceivedEvent
    ) {
        val notification = notificationReceivedEvent.notification

        val mutableNotification = notification.mutableCopy()

        val data = notification.additionalData

        mutableNotification.setExtender { builder ->
            if (data.has("post_id")) {
                val deepLinkIntent = Intent(
                    Intent.ACTION_VIEW,
                    "https://www.myanmarlabournews.com/posts/${data.getString("post_id")}".toUri(),
                    context,
                    MainActivity::class.java
                )

                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(deepLinkIntent)
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                builder.setContentIntent(deepLinkPendingIntent)
            }
            return@setExtender builder
        }

        notificationReceivedEvent.complete(mutableNotification)
    }
}