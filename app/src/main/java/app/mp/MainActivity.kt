package app.mp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import app.mp.common.util.PermissionHandler
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.databinding.ActivityMainBinding
import app.mp.model.service.AudioPlayerService
import app.mp.view.widget.player.BottomPlayerView
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<AudioViewModel>()

    @Inject
    lateinit var playerServiceBinder: PlayerServiceBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PermissionHandler.askPermissionsWhenAppStarted(this)
        startService(AudioPlayerService.getActionIntent(this, AudioPlayerService.Action.START))
        playerServiceBinder.bindServiceTo(this)

        BottomPlayerView(
            view = binding.playerView,
            viewModel = viewModel,
            activity = this,
        ).setup()
    }

    override fun onResume() {
        super.onResume()
        playerServiceBinder.bindServiceTo(this)
    }

    override fun onStop() {
        super.onStop()
        playerServiceBinder.unbindServiceFrom(this)
    }
}