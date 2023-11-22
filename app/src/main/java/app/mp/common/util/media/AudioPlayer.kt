package app.mp.common.util.media

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object AudioPlayer {
    fun playTrack(remoteUrl: String, context: Context) {
        val player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(remoteUrl))
            prepare()
            play()
        }
    }

}