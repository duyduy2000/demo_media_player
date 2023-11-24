package app.mp.model.service

import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import app.mp.common.util.PermissionHandler
import app.mp.common.util.media.AudioPlayer
import app.mp.common.util.media.AudioPlayerNotification
import app.mp.common.util.media.PlayerListener

class AudioPlayerService : MediaSessionService() {
    private lateinit var audioPlayer: AudioPlayer
    private lateinit var notification: AudioPlayerNotification
    private val player
        get() = audioPlayer.mediaSession?.player

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        audioPlayer.mediaSession

    override fun onCreate() {
        super.onCreate()
        audioPlayer = AudioPlayer(this)
        notification = AudioPlayerNotification(this, audioPlayer)

        player?.addListener(
            PlayerListener(
                audioPlayer = audioPlayer,
                notification = notification
            )
        )
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = player!!
        if (!player.playWhenReady || player.mediaItemCount == 0) {
            stopSelf()
        }
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
                player?.pause()
                notification.updateOnPlayerStateChange()
            }

            Action.PLAY.name -> {
                player?.play()
                notification.updateOnPlayerStateChange()
            }
            Action.REPEAT_ONE.name -> {
                player?.repeatMode = Player.REPEAT_MODE_ONE
                notification.updateOnPlayerStateChange()
            }
            Action.REPEAT_ALL.name -> {
                player?.repeatMode = Player.REPEAT_MODE_ALL
                notification.updateOnPlayerStateChange()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if (!PermissionHandler.checkForegroundServicePermission(this)) {
            stopSelf()
            return
        }

        val serviceType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        else 0

        ServiceCompat.startForeground(
            /* service = */this,
            /* id = */ notificationId,
            /* notification = */ notification.self,
            /* foregroundServiceType = */ serviceType,
        )

        audioPlayer.addAudiosFromUri(listOf(
            "https://cdn.freesound.org/previews/680/680316_10399452-hq.mp3",
            "https://cdn.freesound.org/previews/213/213524_862453-hq.mp3"
        ))
        audioPlayer.play()
    }

    enum class Action { START, STOP, PLAY, PAUSE, NEXT, PREVIOUS, REPEAT_ALL, REPEAT_ONE }

    companion object {
        const val channelId = "Audio player"
        const val notificationId = 1

        fun getActionIntent(context: Context, action: Action) = Intent(
            context,
            AudioPlayerService::class.java
        ).apply { this.action = action.name }
    }

}

