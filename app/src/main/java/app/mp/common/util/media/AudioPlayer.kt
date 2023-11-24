package app.mp.common.util.media

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.Callback

class AudioPlayer(context: Context, callback: Callback) {
    var mediaSession: MediaSession? = null
        private set

    init {
        val player = ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(USAGE_MEDIA)
                    .build(),
                true
            )
        }
        mediaSession = MediaSession.Builder(context, player).setCallback(callback).build()
    }

    fun release() {
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
    }

    fun playAudio(remoteUrl: String) {
        val audio = MediaItem.Builder()
            .setUri(remoteUrl)
            .setMediaId("1")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Song 1")
                    .setArtist("Artist 1")
                    .build()
            )
            .build()
        mediaSession?.player?.apply {
            setMediaItem(audio)
            prepare()
            play()
        }
    }

}