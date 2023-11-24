package app.mp.common.util.media

import androidx.media3.common.Player

class PlayerListener(
    private val audioPlayer: AudioPlayer,
    private val notification: AudioPlayerNotification,
) : Player.Listener {
    private val player
        get() = audioPlayer.mediaSession?.player!!

    override fun onPlaybackStateChanged(playbackState: Int) {
        // Change icon Pause to Play when track ends
        if (player.playbackState == Player.STATE_ENDED) {
            notification.updateOnPlayerStateChange()
        }
        super.onPlaybackStateChanged(playbackState)
    }
}