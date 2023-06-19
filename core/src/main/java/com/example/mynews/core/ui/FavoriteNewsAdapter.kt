package com.example.mynews.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.core.R
import com.example.mynews.core.databinding.ItemListNewsBinding
import com.example.mynews.core.domain.model.News
import java.util.ArrayList

class FavoriteNewsAdapter : RecyclerView.Adapter<FavoriteNewsAdapter.ListViewHolder>() {
    private var listData = ArrayList<News>()
    var onItemClick: ((News) -> Unit)? = null

    fun setData(newListData: List<News>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(NewsDiffCallback(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_news, parent, false)
        return ListViewHolder(itemView)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListNewsBinding.bind(itemView)

        fun bind(data: News) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .into(ivItemImage)
                val dateTime = data.publishedAt
                val date = dateTime.substring(0, 10)
                val time = dateTime.substring(11, 16)
                val formattedDate = "$date $time"
                tvItemPublish.text = formattedDate
                tvItemAuthor.text = data.author
                tvItemTitle.text = data.title
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    private class NewsDiffCallback(
        private val oldList: List<News>,
        private val newList: List<News>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].url == newList[newItemPosition].url

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}