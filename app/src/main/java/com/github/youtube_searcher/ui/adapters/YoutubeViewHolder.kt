package com.github.youtube_searcher.ui.adapters

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem

class YoutubeViewHolder(
    view: View,
    val onCheck: (item: MappedYoutubeItem, isChecked: Boolean) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun bind(item: MappedYoutubeItem) {
        val titleView = itemView.findViewById<TextView>(R.id.title)
        val descriptionView = itemView.findViewById<TextView>(R.id.description)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val checkBox = imageView.findViewById<CheckBox>(R.id.checkBox)

        checkBox.isChecked = false
        titleView.text = item.title
        descriptionView.text = item.title
        imageView.load(item.imageUrl)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheck(item, isChecked)
        }
    }
}

