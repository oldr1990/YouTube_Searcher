package com.github.youtube_searcher.ui.playlist

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem

class PlaylistViewHolder(
    view: View,
    val deleteClick: (itemToDelete: MappedYoutubeItem)-> Unit
) : RecyclerView.ViewHolder(view){
    fun bind(item:MappedYoutubeItem){
        Log.i("!@#", "PlaylistViewHolder bind() $item")
        val titleView = itemView.findViewById<TextView>(R.id.playlist_title)
        val descriptionView = itemView.findViewById<TextView>(R.id.playlist_description)
        val imageView = itemView.findViewById<ImageView>(R.id.playlist_imageView)
        val deleteIcon = itemView.findViewById<ImageView>(R.id.playlist_delete)

        titleView.text = item.title
        descriptionView.text = item.title
        imageView.load(item.imageUrl)
        titleView.setOnClickListener {
            Toast.makeText(itemView.context, item.title,Toast.LENGTH_SHORT).show()
        }
        deleteIcon.setOnClickListener { deleteClick(item)}
    }
}