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
    private var isPlaying = true
    private var repeatMode: Int
    private val player get() = audioPlayer.mediaSession?.player!!
    var self: Notification

    init {
        repeatMode = player.repeatMode
        self = buildNotification()
    }

    private fun buildNotification() =
        NotificationCompat.Builder(service, AudioPlayerService.channelId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.round_music_note_36)
            .addAction(
                R.drawable.round_skip_previous_36,
                "Previous",
                getActionIntent(Action.PREVIOUS)
            )
            .addAction(
                if (isPlaying) R.drawable.round_pause_36 else R.drawable.round_play_arrow_36,
                "Play / Pause",
                if (isPlaying) getActionIntent(Action.PAUSE) else getActionIntent(Action.PLAY)
            )
            .addAction(
                R.drawable.round_skip_next_36,
                "Next",
                getActionIntent(Action.NEXT)
            )
            .addAction(
                when (repeatMode) {
                    Player.REPEAT_MODE_ALL -> R.drawable.round_repeat_36
                    else -> R.drawable.round_repeat_one_36
                },
                "Repeat all / one",
                when (repeatMode) {
                    Player.REPEAT_MODE_ALL -> getActionIntent(Action.REPEAT_ONE)
                    else -> getActionIntent(Action.REPEAT_ALL)
                }
            )
            .addAction(
                R.drawable.round_close_36,
                "Next",
                getActionIntent(Action.STOP)
            )
            .setContentTitle("Sample Track")
            .setContentText("Unknown Artist")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(MediaStyle().setShowActionsInCompactView(0, 1, 2))
            .build()

    private fun rebuild() {
        isPlaying = player.isPlaying
        repeatMode = player.repeatMode
        self = buildNotification()
    }

    @SuppressLint("MissingPermission")
    fun updateOnPlayerStateChange() {
        rebuild()
        NotificationManagerCompat.from(service).notify(AudioPlayerService.notificationId, self)
    }

    private fun getActionIntent(action: Action) = PendingIntent.getService(
        service,
        0,
        AudioPlayerService.getActionIntent(service, action),
        PendingIntent.FLAG_IMMUTABLE
    )

}