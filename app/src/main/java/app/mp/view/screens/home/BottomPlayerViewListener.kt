package app.mp.view.screens.home

import androidx.lifecycle.LifecycleOwner
import app.mp.R
import app.mp.databinding.ViewPlayerBottomBinding
import app.mp.viewmodel.home.HomeViewModel

class BottomPlayerViewListener(
    private val view: ViewPlayerBottomBinding,
    private val viewModel: HomeViewModel,
    private val lifecycleOwner: LifecycleOwner,
) {
    fun listenToPlayerStateChange() {
        viewModel.playerState.observe(lifecycleOwner) {
            if (it.isPlaying)
                view.btnPlay.setImageResource(R.drawable.round_pause_36)
            else
                view.btnPlay.setImageResource(R.drawable.round_play_arrow_36)
        }
    }

    fun listenToCurrentTrackStateChange() {
        viewModel.currentTrackState.observe(lifecycleOwner) {
            // Audio's name will be blank if there is no audio in playlist
            view.txtName.text = if (it.name != "") it.name else "No audio"
        }
    }
}