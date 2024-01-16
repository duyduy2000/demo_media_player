package app.mp.view.widget.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.mp.databinding.ItemViewAudioBinding
import app.mp.model.model.Audio

class OldAudioListAdapter(private val context: Context) :
    ListAdapter<Audio, OldAudioListAdapter.ViewHolder>(AudioComparator()) {

//    private var onClick: (Audio, Int) -> Unit = { _, _ -> }
//    var selectedItemIndex: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewAudioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.apply {
//            bind(audio = getItem(position))
//
//            if (position != selectedItemIndex) {
//                itemView.background = ContextCompat.getDrawable(context, R.color.transparent)
//                playingIcon.visibility = View.INVISIBLE
//            } else {
//                itemView.background = ContextCompat.getDrawable(context, R.color.alpha_10)
//                playingIcon.visibility = View.VISIBLE
//
//            }
//
//            itemView.setOnClickListener {
//                onClick(getItem(position), position)
//                selectedItemIndex = holder.adapterPosition
//            }
//        }
    }

    fun onItemClick(listener: (Audio, Int) -> Unit) {
//        onClick = listener
    }

    class ViewHolder(binding: ItemViewAudioBinding) : RecyclerView.ViewHolder(binding.root) {
//        private val audioTitle: TextView
//        private val authorTitle: TextView
//        val playingIcon: ImageView

        init {
//            audioTitle = binding.tvAudioTitle
//            authorTitle = binding.tvAudioAuthor
//            playingIcon = binding.imgAudioPlaying
        }

        fun bind(audio: Audio) {
//            audioTitle.text = audio.name
//            authorTitle.text = audio.author
        }
    }

    class AudioComparator : DiffUtil.ItemCallback<Audio>() {
        override fun areItemsTheSame(oldItem: Audio, newItem: Audio) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Audio, newItem: Audio) =
            oldItem.name == newItem.name && oldItem.author == newItem.author

    }
}