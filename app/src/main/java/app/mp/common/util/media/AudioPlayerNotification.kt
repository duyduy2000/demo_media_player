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
        self = buildNotification()
        repeatMode = player.repeatMode
    }

    private fun buildNotification(): Notification {
        val pauseIntent = getActionIntent(Action.PAUSE)
        val playIntent = getActionIntent(Action.PLAY)
        val toNextTrackIntent = getActionIntent(Action.NEXT)
        val toPreviousTrackIntent = getActionIntent(Action.PREVIOUS)
        val stopServiceIntent = getActionIntent(Action.STOP)
        val setRepeatAllIntent = getActionIntent(Action.REPEAT_ALL)
        val setRepeatOneIntent = getActionIntent(Action.REPEAT_ONE)

        return NotificationCompat.Builder(service, AudioPlayerService.channelId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.round_music_note_24)
            .addAction(R.drawable.round_skip_previous_24, "Previous", toPreviousTrackIntent)
            .addAction(
                if (isPlaying) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24,
                "Play / Pause",
                if (isPlaying) pauseIntent else playIntent
            )
            .addAction(R.drawable.round_skip_next_24, "Next", toNextTrackIntent)
            .addAction(
                if (repeatMode == Player.REPEAT_MODE_ALL) R.drawable.round_repeat_24 else R.drawable.round_repeat_one_24,
                "Repeat all / one",
                if (repeatMode == Player.REPEAT_MODE_ALL) setRepeatOneIntent else setRepeatAllIntent
            )
            .addAction(R.drawable.round_close_24, "Next", stopServiceIntent)
            .setContentTitle("Sample Track")
            .setContentText("Unknown Artist")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(MediaStyle().setShowActionsInCompactView(0, 1, 2))
            .build()
    }

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