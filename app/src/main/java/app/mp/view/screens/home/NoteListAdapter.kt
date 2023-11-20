package app.mp.view.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.mp.databinding.ItemViewTrackBinding
import app.mp.model.local.entity.TrackEntity

class NoteListAdapter :
    ListAdapter<TrackEntity, NoteListAdapter.ViewHolder>(NoteComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(track = getItem(position))
    }

    class ViewHolder(binding: ItemViewTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        private val textView: TextView

        init {
            textView = binding.tvNoteTitle
        }

        fun bind(track: TrackEntity) {
            textView.text = track.title
        }
    }

    class NoteComparator : DiffUtil.ItemCallback<TrackEntity>() {
        override fun areItemsTheSame(oldItem: TrackEntity, newItem: TrackEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TrackEntity, newItem: TrackEntity) =
            oldItem.title == newItem.title

    }
}