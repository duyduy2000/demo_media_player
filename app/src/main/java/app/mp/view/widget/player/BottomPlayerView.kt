package app.mp.view.widget.player

import androidx.fragment.app.FragmentActivity
import app.mp.R
import app.mp.common.di.AppDependencies
import app.mp.databinding.ViewPlayerBottomBinding
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.EntryPointAccessors

class BottomPlayerView(
    private val view: ViewPlayerBottomBinding,
    private val viewModel: AudioViewModel,
    private val activity: FragmentActivity,
) {
    private val serviceBinder = EntryPointAccessors.fromApplication(
        activity,
        AppDependencies::class.java,
    ).serviceBinder()

    fun setup() {
        listenToPlayerStateChange()
        listenToCurrentTrackStateChange()

        PlayerButtons(
            playerBinder = serviceBinder,
            btnPlay = view.btnPlay,
            btnNext = view.btnNext,
            btnPrevious = view.btnPrev
        ).build()
    }

    private fun listenToPlayerStateChange() {
        viewModel.playerState.observe(activity) {
            if (it.isPlaying)
                view.btnPlay.setImageResource(R.drawable.round_pause_36)
            else
                view.btnPlay.setImageResource(R.drawable.round_play_arrow_36)
        }
    }

    private fun listenToCurrentTrackStateChange() {
        viewModel.currentTrackState.observe(activity) {
            // Audio's name will be blank if there is no audio in playlist
            view.txtName.text = if (it.name != "") it.name else "No audio"
        }
    }
}