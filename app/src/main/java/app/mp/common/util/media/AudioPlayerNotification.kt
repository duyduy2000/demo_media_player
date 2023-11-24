package app.mp.common.util.media

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import app.mp.R
import app.mp.model.service.AudioPlayerService
import app.mp.model.service.AudioPlayerService.Action

class AudioPlayerNotification(private val service: Service) {

    fun build(isPlaying: Boolean): Notification {
        val pauseAction = getActionPendingIntent(Action.PAUSE)
        val playAction = getActionPendingIntent(Action.PLAY)

        return NotificationCompat.Builder(service, AudioPlayerService.channelId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.round_music_note_24)
            .addAction(
                if(isPlaying) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24,
                "Play / Pause",
                if (isPlaying) pauseAction else playAction
            )
//            .addAction(R.drawable.round_pause_24, "Pause", pauseAction)
            .setContentTitle("Sample Track")
            .setContentText("Unknown Artist")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(MediaStyle().setShowActionsInCompactView(0))
            .build()
    }

    private fun getActionPendingIntent(action: Action) = PendingIntent.getService(
        service,
        0,
        AudioPlayerService.getActionIntent(service, action),
        PendingIntent.FLAG_IMMUTABLE
    )

}