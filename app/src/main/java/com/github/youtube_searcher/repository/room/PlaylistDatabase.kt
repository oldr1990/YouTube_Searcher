package com.github.youtube_searcher.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.youtube_searcher.model.room.RoomItem

@Database(entities = [RoomItem::class], version = 1)
abstract class PlaylistDatabase():RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}