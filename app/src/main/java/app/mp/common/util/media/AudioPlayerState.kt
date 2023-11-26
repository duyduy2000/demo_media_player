package app.mp.common.util.media

import androidx.media3.common.Player
import kotlinx.coroutines.flow.MutableStateFlow

class AudioPlayerState(
    var playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState())
) {
    data class PlayerState(
        var playbackState: Int = Player.STATE_ENDED,
        var isPlaying: Boolean = true,
        var repeatMode: Int = Player.REPEAT_MODE_ALL,
    )
}

