package com.github.youtube_searcher.repository.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.github.youtube_searcher.model.room.RoomItem

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM PLAYLIST_TABLE")
    fun getPlaylist():List<RoomItem>
    @Insert(onConflict = REPLACE)
    fun insertToPlaylist(listOfRoomItems: List<RoomItem>)
    @Delete
    fun delete(roomItem: RoomItem)
}