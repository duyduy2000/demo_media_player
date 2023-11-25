package app.mp.view.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.R
import app.mp.databinding.FragmentHomeScreenBinding
import app.mp.model.service.AudioPlayerService
import app.mp.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val noteListAdapter = NoteListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.rvNoteList.adapter = noteListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.allTracks.observe(viewLifecycleOwner) { noteList ->
//            noteListAdapter.submitList(noteList)
//        }
//        viewModel.getTrack(it)

        val startService =
            AudioPlayerService.getActionIntent(requireContext(), AudioPlayerService.Action.START)
        requireActivity().startService(startService)

        viewModel.playerState.observe(viewLifecycleOwner) {
            val playerView = binding.playerView
            Log.e("audio", it.toString())
            if (it.isPlaying)
                playerView.btnPlay.setImageResource(R.drawable.round_pause_36)
            else
                playerView.btnPlay.setImageResource(R.drawable.round_play_arrow_36)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}