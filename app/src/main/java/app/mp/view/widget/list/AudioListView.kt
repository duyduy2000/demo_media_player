package app.mp.view.widget.list

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.mp.R
import app.mp.common.di.AppDependencies
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.EntryPointAccessors

class AudioListView(
    private val fragment: Fragment,
    private val view: RecyclerView,
    private val viewModel: AudioViewModel,
) {
    private val adapter = AudioListAdapter(context)
    private val context get() = fragment.requireContext()
    private val serviceBinder =
        EntryPointAccessors.fromApplication(context, AppDependencies::class.java).serviceBinder()

    init {
        view.adapter = adapter
        addDividerToTrackListView()
        setUpdateListOnDataChange()
        setChangeAudioOnItemClick()
        setChangeItemLayoutOnAudioChange()
    }

    private fun addDividerToTrackListView() {
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        val dividerView = ContextCompat.getDrawable(context, R.drawable.divider_audio_list)!!
        divider.setDrawable(dividerView)
        view.addItemDecoration(divider)
    }

    private fun setUpdateListOnDataChange() {
        viewModel.audioList.observe(fragment.viewLifecycleOwner) {
            serviceBinder.usePlayer { if (it.isNotEmpty()) adapter.submitList(it) }
        }
    }

    private fun setChangeAudioOnItemClick() {
        adapter.onItemClick { _, index ->
            serviceBinder.usePlayer {
                viewModel.audioList.value?.let { addAudios(it) }
                playAudioByIndex(index)
            }
        }
    }

    private fun setChangeItemLayoutOnAudioChange() {
        viewModel.currentTrackState.observe(fragment.viewLifecycleOwner) {
            val currentIndex = it.index
            val prevIndex = adapter.selectedItemIndex
            adapter.apply {
                prevIndex?.let { index -> notifyItemChanged(index) }
                adapter.selectedItemIndex = currentIndex
                currentIndex?.let { index -> notifyItemChanged(index) }
            }
        }
    }

}