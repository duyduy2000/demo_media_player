package app.mp.view.screens.local_audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.mp.databinding.FragmentLocalAudioScreenBinding
import app.mp.view.widget.fragment.AudioListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalAudioScreen : AudioListFragment() {
    private var _binding: FragmentLocalAudioScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocalAudioScreenBinding.inflate(inflater, container, false)
        initView(context = requireContext(), recyclerView = binding.rvAudioList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllLocalAudios(context = requireContext())
        setUpdateListOnDataChange()
        setChangeAudioOnItemClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}