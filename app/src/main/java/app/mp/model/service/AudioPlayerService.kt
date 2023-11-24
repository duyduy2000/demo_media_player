package app.mp.model.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import app.mp.common.util.media.AudioPlayer
import app.mp.common.util.media.AudioPlayerNotification

class AudioPlayerService : MediaSessionService(), MediaSession.Callback {
    private lateinit var audioPlayer: AudioPlayer
    private val serviceType =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        else 0
    private lateinit var notification: Notification

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        audioPlayer.mediaSession

    override fun onCreate() {
        super.onCreate()
        audioPlayer = AudioPlayer(this, this)
        notification = AudioPlayerNotification(this).build(true)
    }

    override fun onDestroy() {
        audioPlayer.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.name -> start()
            Action.STOP.name -> stopSelf()
            Action.PAUSE.name -> {
                audioPlayer.mediaSession?.player?.pause()
                updateNotificationOnPlayerStateChange()
            }

            Action.PLAY.name -> {
                audioPlayer.mediaSession?.player?.play()
                updateNotificationOnPlayerStateChange()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if (!checkIfPermissionGranted()) {
            stopSelf()
            return
        }

        ServiceCompat.startForeground(
            /* service = */this,
            /* id = */ notificationId,
            /* notification = */ notification,
            /* foregroundServiceType = */ serviceType,
        )

        audioPlayer.playAudio("https://cdn.freesound.org/previews/680/680316_10399452-hq.mp3")
    }

    private fun checkIfPermissionGranted(): Boolean {
        val checkPermission = { permission: String ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }

        val foregroundServicePermission =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && checkPermission(Manifest.permission.FOREGROUND_SERVICE))
                    || Build.VERSION.SDK_INT < Build.VERSION_CODES.P

        val postNotificationPermission =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && checkPermission(Manifest.permission.POST_NOTIFICATIONS))
                    || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

        return foregroundServicePermission && postNotificationPermission
    }

    @SuppressLint("MissingPermission")
    private fun updateNotificationOnPlayerStateChange() {
        val playerState = audioPlayer.mediaSession?.player!!.isPlaying
        notification = AudioPlayerNotification(this).build(isPlaying = playerState)
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }

    enum class Action { START, STOP, PLAY, PAUSE }

    companion object {
        const val channelId = "Audio player"
        const val notificationId = 1

        fun getActionIntent(context: Context, action: Action) = Intent(
            context,
            AudioPlayerService::class.java
        ).apply {
            this.action = action.name
        }
    }

}