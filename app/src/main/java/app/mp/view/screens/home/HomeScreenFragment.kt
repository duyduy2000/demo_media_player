package app.mp.view.screens.home

import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import app.mp.databinding.FragmentHomeScreenBinding
import app.mp.model.service.AudioPlayerService
import app.mp.viewmodel.home.HomeViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val noteListAdapter = NoteListAdapter()
    private lateinit var controllerFuture: ListenableFuture<MediaController>

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
//        context?.let { viewModel.getTrack(it) }

        val startService =
            AudioPlayerService.getActionIntent(requireContext(), AudioPlayerService.Action.START)
        requireActivity().startService(startService)

        val sessionToken = SessionToken(
            requireContext(),
            ComponentName(this.requireContext(), AudioPlayerService::class.java)
        )
        var mediaController: MediaController? = null
        controllerFuture =
            MediaController.Builder(requireContext(), sessionToken).buildAsync().apply {
                addListener(
                    /* listener = */ {
                        mediaController = controllerFuture.get()

                        val audio = MediaItem.Builder()
                            .setUri("https://cdn.freesound.org/previews/680/680316_10399452-hq.mp3")
                            .setMediaId("1")
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setTitle("Song 1")
                                    .setArtist("Artist 1")
                                    .build()
                            )
                            .build()
                        mediaController!!.apply {
                            setMediaItem(audio)
                            prepare()
                            play()
                        }
                    },
                    /* executor = */ MoreExecutors.directExecutor()
                )
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        MediaController.releaseFuture(controllerFuture)
        super.onStop()
    }

}