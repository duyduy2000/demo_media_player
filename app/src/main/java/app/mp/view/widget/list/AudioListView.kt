package app.mp.view.widget.list

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.mp.R
import app.mp.view.screens.online_search.AudioListAdapter

class AudioListView(private val context: Context, private val view: RecyclerView) {
    val adapter = AudioListAdapter()

    fun init() {
        view.adapter = adapter
        addDividerToTrackListView()
    }

    private fun addDividerToTrackListView() {
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        val dividerView =
            ContextCompat.getDrawable(context, R.drawable.divider_audio_list)!!
        divider.setDrawable(dividerView)
        view.addItemDecoration(divider)
    }
}