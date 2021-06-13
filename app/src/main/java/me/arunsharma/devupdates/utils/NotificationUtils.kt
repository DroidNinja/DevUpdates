package me.arunsharma.devupdates.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arunsharma.devupdates.ui.MainActivity
import javax.inject.Inject

class NotificationUtils @Inject constructor(@ApplicationContext private val context: Context) {

    private var channel: NotificationChannel? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = createNotificationChannel(
                DEFAULT_CHANNEL_ID,
                DEFAULT_CHANNEL_NAME
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(id: String, name: String): NotificationChannel {
        return NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
    }

    fun sendNotification(
        title: String,
        content: String,
        icon: Int
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)
    }

    companion object {

        const val DEFAULT_CHANNEL_ID = "1"
        const val DEFAULT_CHANNEL_NAME = "Periodic Feed Updates"
    }
}