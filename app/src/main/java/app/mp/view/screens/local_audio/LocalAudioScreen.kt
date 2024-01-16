package app.mp.view.screens.local_audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.databinding.FragmentLocalAudioScreenBinding
import app.mp.view.widget.list.AudioListView
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalAudioScreen : Fragment() {
    private var _binding: FragmentLocalAudioScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AudioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocalAudioScreenBinding.inflate(inflater, container, false)
        binding.localAudioList.setContent {
            AudioListView(viewModel = viewModel, context = requireContext()).Build()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllLocalAudios(context = requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}