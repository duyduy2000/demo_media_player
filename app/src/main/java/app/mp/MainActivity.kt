package app.mp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import app.mp.common.util.PermissionHandler
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.databinding.ActivityMainBinding
import app.mp.view.screens.audio_search.BottomPlayerViewListener
import app.mp.view.widget.player.PlayerButtons
import app.mp.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val playerServiceBinder = PlayerServiceBinder()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PermissionHandler.askPermissionsWhenAppStarted(this)

        playerServiceBinder.bindServiceTo(this)
        viewModel.getAllLocalTracks(this)

        setupPlayerView()

        binding.playerView.btnQueue.setOnClickListener {
            if (playerServiceBinder.isBound && viewModel.localTrackList.value != null) {
                playerServiceBinder.service.audioPlayer.addLocalTracks(viewModel.localTrackList.value!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        playerServiceBinder.bindServiceTo(this)
    }

    override fun onStop() {
        super.onStop()
        playerServiceBinder.unbindServiceFrom(this)
    }

    private fun setupPlayerView() {
        BottomPlayerViewListener(
            view = binding.playerView,
            viewModel = viewModel,
            lifecycleOwner = this
        ).apply {
            listenToPlayerStateChange()
            listenToCurrentTrackStateChange()
        }

        PlayerButtons(
            playerBinder = playerServiceBinder,
            btnPlay = binding.playerView.btnPlay,
            btnNext = binding.playerView.btnNext,
            btnPrevious = binding.playerView.btnPrev
        ).build()
    }
}