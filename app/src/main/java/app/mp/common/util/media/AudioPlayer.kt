package app.mp.common.util.media

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PercentageRating
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import app.mp.model.model.Track

class AudioPlayer(context: Context) {
    var mediaSession: MediaSession?
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
            repeatMode = Player.REPEAT_MODE_ALL
        }
        mediaSession = MediaSession.Builder(context, player).build()
    }

    fun release() {
        mediaSession?.apply {
            player.release()
            release()
            mediaSession = null
        }
    }

    fun addAudios(trackList: List<Track>) = mediaSession?.player?.apply {
        for (track in trackList) {
            addMediaItem(
                MediaItem.Builder()
                    .setMediaId(track.id.toString())
                    .setTag(track.tags)
                    .setUri(track.previewHqMp3)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(track.name)
                            .setArtist(track.username)
                            .setDescription(track.description)
                            .setUserRating(PercentageRating(track.averageRating.toFloat()))
                            .build()
                    )
                    .build()
            )
        }
        prepare()
    }

    companion object {
        fun Player.goToNextMediaItem() {
            seekToNextMediaItem()
            if (!playWhenReady) play()
        }

        fun Player.goToPrevMediaItem() {
            seekToPreviousMediaItem()
            if (!playWhenReady) play()
        }
    }
}