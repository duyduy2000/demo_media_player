package app.mp.common.util.media

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession

class AudioPlayer(context: Context) {
    var mediaSession: MediaSession? = null
        private set

    init {
        val player = ExoPlayer.Builder(context).build()
        mediaSession = MediaSession.Builder(context, player).build()
    }

    fun release() {
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
    }


    companion object {
        fun playAudio(remoteUrl: String, context: Context) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(remoteUrl))
                prepare()
                play()
            }
        }
    }

}