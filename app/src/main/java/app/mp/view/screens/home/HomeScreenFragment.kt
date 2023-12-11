package app.mp.view.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val playerServiceBinder = PlayerServiceBinder()
    private val trackListAdapter = TrackListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.rvNoteList.adapter = trackListAdapter
        addDividerToTrackListView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AudioPlayerService.getActionIntent(requireContext(), AudioPlayerService.Action.START)
            .apply { requireContext().startService(this) }
        playerServiceBinder.bindServiceTo(this)

        viewModel.getSimilarTrack()
        viewModel.getAllLocalTracks(requireContext())

        viewModel.trackList.observe(viewLifecycleOwner) {
            trackListAdapter.submitList(it)
            if (playerServiceBinder.isBound && it.isNotEmpty()) {
                playerServiceBinder.service.audioPlayer.addTracks(it)
            }
        }

        setupPlayerView()
    }

    override fun onResume() {
        super.onResume()
        playerServiceBinder.bindServiceTo(this)
    }

    override fun onStop() {
        super.onStop()
        playerServiceBinder.unbindServiceFrom(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addDividerToTrackListView() {
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val dividerView =
            ContextCompat.getDrawable(requireContext(), R.drawable.divider_audio_list)!!
        divider.setDrawable(dividerView)
        binding.rvNoteList.addItemDecoration(divider)
    }

    private fun setupPlayerView() {
        BottomPlayerViewListener(
            view = binding.playerView, viewModel = viewModel, lifecycleOwner = viewLifecycleOwner
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