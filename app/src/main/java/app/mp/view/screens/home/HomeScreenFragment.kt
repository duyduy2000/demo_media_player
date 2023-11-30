package app.mp.view.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.R
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.databinding.FragmentHomeScreenBinding
import app.mp.model.service.AudioPlayerService
import app.mp.view.widget.player.PlayerButtons
import app.mp.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val trackListAdapter = TrackListAdapter()
    private val playerServiceBinder = PlayerServiceBinder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.rvNoteList.adapter = trackListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            getSimilarTrack()
        }

        AudioPlayerService.getActionIntent(requireContext(), AudioPlayerService.Action.START)
            .apply {
                requireActivity().startService(this)
                requireActivity().bindService(this, playerServiceBinder, Context.BIND_AUTO_CREATE)
            }

        viewModel.trackList.observe(viewLifecycleOwner) {
            trackListAdapter.submitList(it)
            if (playerServiceBinder.isBound && it!!.isNotEmpty()) {
                playerServiceBinder.service.audioPlayer.addAudios(it)
            }
        }

        viewModel.playerState.observe(viewLifecycleOwner) {
            val playerView = binding.playerView
            if (it.isPlaying)
                playerView.btnPlay.setImageResource(R.drawable.round_pause_36)
            else
                playerView.btnPlay.setImageResource(R.drawable.round_play_arrow_36)
        }

        viewModel.currentTrackState.observe(viewLifecycleOwner) {
            val playerView = binding.playerView
            playerView.txtName.text = it.name
        }

        PlayerButtons(
            playerBinder = playerServiceBinder,
            btnPlay = binding.playerView.btnPlay,
            btnNext = binding.playerView.btnNext,
            btnPrevious = binding.playerView.btnPrev
        ).build()

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(playerServiceBinder)
        playerServiceBinder.isBound = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}