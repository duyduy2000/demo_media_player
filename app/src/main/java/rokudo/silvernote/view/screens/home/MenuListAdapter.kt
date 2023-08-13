package rokudo.silvernote.view.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rokudo.silvernote.databinding.ItemViewHomeMenuBinding

class MenuListAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemViewHomeMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data = dataList[position])
    }

    override fun getItemCount(): Int = dataList.count()

    class ViewHolder(binding: ItemViewHomeMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        private val textView: TextView

        init {
            textView = binding.tvItemName
        }

        fun bind(data: String) {
            textView.text = data
        }
    }
}