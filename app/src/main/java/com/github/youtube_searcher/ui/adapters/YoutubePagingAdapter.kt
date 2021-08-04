package com.github.youtube_searcher.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem

class YoutubePagingAdapter(private val onCheckListener: (item: MappedYoutubeItem, isChecked: Boolean) -> Unit) :
    PagingDataAdapter<MappedYoutubeItem, YoutubeViewHolder>(YoutubeDifferentiator) {


    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val item = getItem(position) ?: MappedYoutubeItem("", "", "")
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        return YoutubeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.youtube_paging_item, parent, false),
            onCheckListener
        )
    }

    object YoutubeDifferentiator : DiffUtil.ItemCallback<MappedYoutubeItem>() {
        override fun areItemsTheSame(
            oldItem: MappedYoutubeItem,
            newItem: MappedYoutubeItem
        ): Boolean = oldItem.title == newItem.title

        override fun areContentsTheSame(
            oldItem: MappedYoutubeItem,
            newItem: MappedYoutubeItem
        ): Boolean = oldItem == newItem
    }


}