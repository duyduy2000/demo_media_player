package rokudo.silvernote.view.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rokudo.silvernote.databinding.FragmentHomeScreenBinding
import rokudo.silvernote.viewmodel.home.HomeViewModel

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val noteListAdapter = NoteListAdapter(dataList = emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.rvNoteList.adapter = noteListAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allNotes.observe(viewLifecycleOwner) { noteList ->
            noteListAdapter.submitList(noteList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}