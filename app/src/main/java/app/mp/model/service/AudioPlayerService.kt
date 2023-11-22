package app.mp.model.service

import android.Manifest
import android.app.ForegroundServiceStartNotAllowedException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class AudioPlayerService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private fun start() {
        if (!checkIfPermissionGranted()) {
            stopSelf()
            return
        }

        val notification = NotificationCompat.Builder(this, "Audio player")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle("Sample Track")
            .setContentText("Unknown Artist")
            .build()

        try {
            ServiceCompat.startForeground(
                /* service = */ this,
                /* id = */ 1,
                /* notification = */ notification,
                /* foregroundServiceType = */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                else
                    0,
            )
        } catch (exception: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && exception is ForegroundServiceStartNotAllowedException
            ) {
                stopSelf()
                return
            }
        }
    }

    private fun checkIfPermissionGranted() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.FOREGROUND_SERVICE
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }

    private fun playTrack() {
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

}