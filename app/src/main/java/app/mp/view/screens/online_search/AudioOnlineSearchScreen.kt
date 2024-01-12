package app.mp.view.screens.online_search

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
import app.mp.databinding.FragmentAudioOnlineSearchScreenBinding
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AudioOnlineSearchScreen : Fragment() {

    private var _binding: FragmentAudioOnlineSearchScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AudioViewModel>()
    private val audioListAdapter = AudioListAdapter()

    @Inject
    lateinit var playerServiceBinder: PlayerServiceBinder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAudioOnlineSearchScreenBinding.inflate(inflater, container, false)
        binding.rvAudioList.adapter = audioListAdapter
        addDividerToTrackListView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSimilarAudio(80408)
        viewModel.audioList.observe(viewLifecycleOwner) {
            if (playerServiceBinder.isBound && it.isNotEmpty()) {
                playerServiceBinder.service.audioPlayer.addAudios(it)
                audioListAdapter.submitList(it)
            }
        }

        audioListAdapter.onItemClick = { _, index ->
            if (playerServiceBinder.isBound) {
                playerServiceBinder.service.audioPlayer.playAudioByIndex(index)
            }
        }
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
        binding.rvAudioList.addItemDecoration(divider)
    }

}