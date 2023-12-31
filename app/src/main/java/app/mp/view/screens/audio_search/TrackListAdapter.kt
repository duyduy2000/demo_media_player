package app.mp.view.screens.audio_search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.mp.databinding.ItemViewAudioBinding
import app.mp.model.model.Audio

class TrackListAdapter :
    ListAdapter<Audio, TrackListAdapter.ViewHolder>(TrackComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewAudioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(audio = getItem(position))
    }

    class ViewHolder(binding: ItemViewAudioBinding) : RecyclerView.ViewHolder(binding.root) {
        private val audioTitle: TextView
        private val authorTitle: TextView

        init {
            audioTitle = binding.tvAudioTitle
            authorTitle = binding.tvAudioAuthor
        }

        fun bind(audio: Audio) {
            audioTitle.text = audio.name
            authorTitle.text = audio.username
        }
    }

    class TrackComparator : DiffUtil.ItemCallback<Audio>() {
        override fun areItemsTheSame(oldItem: Audio, newItem: Audio) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Audio, newItem: Audio) =
            oldItem.name == newItem.name && oldItem.username == newItem.username

    }
}