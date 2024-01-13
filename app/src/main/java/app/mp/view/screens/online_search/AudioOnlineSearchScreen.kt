package app.mp.view.screens.online_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.mp.databinding.FragmentAudioOnlineSearchScreenBinding
import app.mp.view.widget.fragment.AudioListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioOnlineSearchScreen : AudioListFragment() {
    private var _binding: FragmentAudioOnlineSearchScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAudioOnlineSearchScreenBinding.inflate(inflater, container, false)
        initView(context = requireContext(), recyclerView = binding.rvAudioList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSimilarAudio(80408)
        setUpdateListOnDataChange()
        setChangeAudioOnItemClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}