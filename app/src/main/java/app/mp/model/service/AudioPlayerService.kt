package app.mp.model.service

import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import app.mp.common.util.PermissionHandler
import app.mp.common.util.media.AudioPlayer
import app.mp.common.util.media.AudioPlayer.Companion.goToNextMediaItem
import app.mp.common.util.media.AudioPlayer.Companion.goToPrevMediaItem
import app.mp.common.util.media.AudioPlayerNotification
import app.mp.common.util.media.AudioPlayerState
import app.mp.common.util.media.PlayerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AudioPlayerService : MediaSessionService() {
    lateinit var audioPlayer: AudioPlayer
    private lateinit var notification: AudioPlayerNotification
    private val binder = LocalBinder()
    val player
        get() = audioPlayer.mediaSession?.player!!

    @Inject
    lateinit var playerState: AudioPlayerState

    inner class LocalBinder : Binder() {
        val service
            get() = this@AudioPlayerService
    }

    override fun onBind(intent: Intent?): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        audioPlayer.mediaSession

    override fun onCreate() {
        super.onCreate()
        initialConfig()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (!player.playWhenReady || player.mediaItemCount == 0) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        audioPlayer.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updatePlayerOnAction(action = intent?.action)
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
    }

    private fun updatePlayerOnAction(action: String?) {
        when(action)  {
            Action.START.name -> start()
            Action.STOP.name -> stopSelf()
            Action.PAUSE.name -> player.pause()
            Action.PLAY.name -> player.play()
            Action.REPEAT_ONE.name -> player.repeatMode = Player.REPEAT_MODE_ONE
            Action.REPEAT_ALL.name -> player.repeatMode = Player.REPEAT_MODE_ALL
            Action.NEXT.name -> player.goToNextMediaItem()
            Action.PREVIOUS.name -> player.goToPrevMediaItem()
        }
    }

    private fun initialConfig() {
        audioPlayer = AudioPlayer(this)
        notification = AudioPlayerNotification(this, audioPlayer)

        player.apply {
            addListener(
                PlayerListener(
                    audioPlayer = audioPlayer,
                    notification = notification,
                    state = playerState
                )
            )
        }
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

