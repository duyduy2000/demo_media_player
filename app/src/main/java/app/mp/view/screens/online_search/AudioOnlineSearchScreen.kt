package app.mp.view.screens.online_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.databinding.FragmentAudioOnlineSearchScreenBinding
import app.mp.view.widget.list.AudioListView
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioOnlineSearchScreen : Fragment() {
    private var _binding: FragmentAudioOnlineSearchScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AudioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAudioOnlineSearchScreenBinding.inflate(inflater, container, false)
        binding.onlineAudioList.setContent {
            AudioListView(viewModel = viewModel, context = requireContext()).Build()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSimilarAudio(80408)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}