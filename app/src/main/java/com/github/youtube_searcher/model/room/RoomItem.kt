package com.github.youtube_searcher.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class RoomItem(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "img_url") val imageUrl: String,
)
