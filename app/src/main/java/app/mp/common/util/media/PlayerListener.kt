package app.mp.common.util.media

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.flow.update

class PlayerListener(
    private val audioPlayer: AudioPlayer,
    private val notification: AudioPlayerNotification,
    private val state: AudioPlayerState,
) : Player.Listener {
    private val player
        get() = audioPlayer.mediaSession?.player!!

    override fun onPlaybackStateChanged(playbackState: Int) {
        // Change icon to "Play" when track ends
        if (player.playbackState == Player.STATE_ENDED) {
            notification.updateOnPlayerStateChange()
        }

        state.playerState.update { state.playerState.value.copy(playbackState = playbackState) }
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        stopPlayerWhenListEmpty()
        state.playerState.update { state.playerState.value.copy(isPlaying = playWhenReady) }
        notification.updateOnPlayerStateChange()
        super.onPlayWhenReadyChanged(playWhenReady, reason)
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        state.playerState.update { state.playerState.value.copy(repeatMode = repeatMode) }
        notification.updateOnPlayerStateChange()
        super.onRepeatModeChanged(repeatMode)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        notification.updateOnPlayerStateChange()
        mediaItem?.let { item ->
            state.currentTrackState.update {
                state.currentTrackState.value.copy(
                    id = item.mediaId,
                    name = item.mediaMetadata.title.toString(),
                )
            }
        }
    }

    private fun stopPlayerWhenListEmpty() {
        if (player.mediaItemCount == 0) player.pause()
    }
}