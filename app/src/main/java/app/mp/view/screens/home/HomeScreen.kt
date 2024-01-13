package app.mp.view.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import app.mp.R
import app.mp.common.util.TimerUtil
import app.mp.databinding.FragmentHomeScreenBinding

class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOnlineSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeScreen_to_audioOnlineSearchScreen)
        }

        binding.btnLocalAudio.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeScreen_to_localAudioScreen)
        }

        askForExitConfirmOnBackButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun askForExitConfirmOnBackButton() {
        var exitConfirm = false

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (exitConfirm) requireActivity().finish()

            Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
            exitConfirm = true

            TimerUtil.runDelayedTask(delayMillis = 2000) { exitConfirm = false }
        }
    }
}