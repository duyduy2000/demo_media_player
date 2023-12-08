package app.mp.view.widget.player

import android.widget.ImageView
import androidx.media3.common.Player
import app.mp.common.util.media.AudioPlayer.Companion.goToNextMediaItem
import app.mp.common.util.media.AudioPlayer.Companion.goToPrevMediaItem
import app.mp.common.util.media.PlayerServiceBinder

private typealias IconButton = ImageView

data class PlayerButtons(
    private val playerBinder: PlayerServiceBinder,
    private val btnPlay: IconButton?,
    private val btnNext: IconButton? = null,
    private val btnPrevious: IconButton? = null,
    private val btnRepeatMode: IconButton? = null,
    private val btnShuffle: IconButton? = null,
) {
    fun build() {
        btnPlay?.setOnPressListener {
            if (it.playWhenReady) it.pause()
            else it.play()
        }
        btnNext?.setOnPressListener { it.goToNextMediaItem() }
        btnPrevious?.setOnPressListener { it.goToPrevMediaItem() }
        btnRepeatMode?.setOnPressListener {
            if (it.repeatMode == Player.REPEAT_MODE_ALL) it.repeatMode = Player.REPEAT_MODE_ONE
            else it.repeatMode = Player.REPEAT_MODE_ALL
        }
        btnShuffle?.setOnPressListener { it.shuffleModeEnabled = !it.shuffleModeEnabled }
    }

    private fun ImageView.setOnPressListener(listener: (player: Player) -> Unit) {
        setOnClickListener {
            if (playerBinder.isBound) listener(playerBinder.service.player)
        }
    }

}

