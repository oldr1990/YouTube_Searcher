package com.github.youtube_searcher.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem

class YoutubePagingAdapter :
    PagingDataAdapter<MappedYoutubeItem, YoutubePagingAdapter.ViewHolder>(YoutubeDifferentiator) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position) ?: MappedYoutubeItem("", "", "")
        Log.i("!@#", "onBindViewHolder $item")
        val titleView = holder.itemView.findViewById<TextView>(R.id.title)
        val descriptionView = holder.itemView.findViewById<TextView>(R.id.description)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.description)
        titleView.text = item.title
        descriptionView.text = item.title
        Glide.with(holder.itemView).load(item.imageUrl).into(imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.youtube_paging_item, parent, false)
        )

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