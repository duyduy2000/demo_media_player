package app.mp.common.util.media

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media3.common.Player
import app.mp.R
import app.mp.model.service.AudioPlayerService
import app.mp.model.service.AudioPlayerService.Action

class AudioPlayerNotification(private val service: Service, private val audioPlayer: AudioPlayer) {
    private val player get() = audioPlayer.mediaSession?.player!!
    lateinit var self: Notification

    init {
        buildNotification()
    }

    private fun buildNotification() {
        self = NotificationCompat.Builder(service, AudioPlayerService.channelId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.round_music_note_36)
            .addAction(
                R.drawable.round_skip_previous_36,
                "Previous",
                getActionIntent(Action.PREVIOUS)
            )
            .addAction(
                if (player.playWhenReady) R.drawable.round_pause_36 else R.drawable.round_play_arrow_36,
                "Play / Pause",
                if (player.playWhenReady) getActionIntent(Action.PAUSE) else getActionIntent(Action.PLAY)
            )
            .addAction(
                R.drawable.round_skip_next_36,
                "Next",
                getActionIntent(Action.NEXT)
            )
            .addAction(
                when (player.repeatMode) {
                    Player.REPEAT_MODE_ALL -> R.drawable.round_repeat_36
                    else -> R.drawable.round_repeat_one_36
                },
                "Repeat all / one",
                when (player.repeatMode) {
                    Player.REPEAT_MODE_ALL -> getActionIntent(Action.REPEAT_ONE)
                    else -> getActionIntent(Action.REPEAT_ALL)
                }
            )
            .addAction(
                R.drawable.round_close_36,
                "Next",
                getActionIntent(Action.STOP)
            )
            .setContentTitle(player.currentMediaItem?.mediaMetadata?.title ?: "Unknown")
            .setContentText(player.currentMediaItem?.mediaMetadata?.artist ?: "Unknown")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(MediaStyle().setShowActionsInCompactView(0, 1, 2))
            .build()
    }

    @SuppressLint("MissingPermission")
    fun updateOnPlayerStateChange() {
        buildNotification()
        NotificationManagerCompat.from(service).notify(AudioPlayerService.notificationId, self)
    }

    private fun getActionIntent(action: Action) = PendingIntent.getService(
        service,
        0,
        AudioPlayerService.getActionIntent(service, action),
        PendingIntent.FLAG_IMMUTABLE
    )

}