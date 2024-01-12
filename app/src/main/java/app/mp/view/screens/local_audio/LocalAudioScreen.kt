package app.mp.view.screens.local_audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.mp.common.util.media.PlayerServiceBinder
import app.mp.databinding.FragmentLocalAudioScreenBinding
import app.mp.viewmodel.audio.AudioViewModel
import javax.inject.Inject

class LocalAudioScreen : Fragment() {
    private var _binding: FragmentLocalAudioScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AudioViewModel>()

    @Inject
    lateinit var playerServiceBinder: PlayerServiceBinder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocalAudioScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}