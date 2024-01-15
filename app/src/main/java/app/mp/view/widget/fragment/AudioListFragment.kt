package app.mp.view.widget.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import app.mp.view.widget.list.AudioListView
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AudioListFragment : Fragment() {
    protected val viewModel by viewModels<AudioViewModel>()
    private lateinit var audioListView: AudioListView

    fun initView(recyclerView: RecyclerView) {
        audioListView = AudioListView(this, view = recyclerView, viewModel = viewModel)
    }
}