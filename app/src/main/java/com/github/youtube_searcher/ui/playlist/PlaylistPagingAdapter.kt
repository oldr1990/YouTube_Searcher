package com.github.youtube_searcher.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.room.RoomItem

class PlaylistPagingAdapter(
    private val deleteClick: (itemToDelete: MappedYoutubeItem) -> Unit
) : PagingDataAdapter<MappedYoutubeItem, PlaylistViewHolder>(PlaylistDifferentiator) {
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val item = getItem(position) ?: MappedYoutubeItem("", "", "")
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder =
        PlaylistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.playlist_item, parent, false),
            deleteClick,
        )

}

object PlaylistDifferentiator : DiffUtil.ItemCallback<MappedYoutubeItem>() {
    override fun areItemsTheSame(
        oldItem: MappedYoutubeItem,
        newItem: MappedYoutubeItem
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: MappedYoutubeItem,
        newItem: MappedYoutubeItem
    ): Boolean = oldItem == newItem
}