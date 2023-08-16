package rokudo.silvernote.view.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rokudo.silvernote.databinding.ItemViewNoteBinding
import rokudo.silvernote.model.local.entity.Note

class NoteListAdapter(private val dataList: List<Note>) :
    ListAdapter<Note, NoteListAdapter.ViewHolder>(NoteComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(note = dataList[position])
    }

    override fun getItemCount(): Int = dataList.count()

    class ViewHolder(binding: ItemViewNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        private val textView: TextView

        init {
            textView = binding.tvNoteTitle
        }

        fun bind(note: Note) {
            textView.text = note.title
        }
    }

    class NoteComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem.title == newItem.title

    }
}