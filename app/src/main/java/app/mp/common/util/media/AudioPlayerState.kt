package app.mp.common.util.media

import androidx.media3.common.Player
import kotlinx.coroutines.flow.MutableStateFlow

class AudioPlayerState(
    val playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState()),
    val currentTrackState: MutableStateFlow<CurrentTrackState> = MutableStateFlow(CurrentTrackState()),
) {
    data class PlayerState(
        val playbackState: Int = Player.STATE_ENDED,
        val isPlaying: Boolean = false,
        val repeatMode: Int = Player.REPEAT_MODE_ALL,
    )

    data class CurrentTrackState(
        val id: String = "",
        val name: String = "",
    )
}

