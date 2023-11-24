package app.mp.common.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import app.mp.model.service.AudioPlayerService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                /* id = */ AudioPlayerService.channelId,
                /* name = */ AudioPlayerService.channelId,
                /* importance = */ importance
            ).apply {
                description = AudioPlayerService.channelId
            }
            channel.registerToSystem()
        }
    }

    private fun NotificationChannel.registerToSystem() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(this)
        }
    }
}