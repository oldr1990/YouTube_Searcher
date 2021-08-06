package com.github.youtube_searcher.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.mappers.PlaylistRoomMapper
import com.github.youtube_searcher.model.mappers.YoutubeResponseMapper
import com.github.youtube_searcher.model.room.RoomItem
import com.github.youtube_searcher.repository.paging_source.YoutubePagingSource
import com.github.youtube_searcher.repository.room.PlaylistDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val youtubeApi: YoutubeApi,
    private val youtubeResponseMapper: YoutubeResponseMapper,
    private val playlistRoomMapper: PlaylistRoomMapper,
    private val playlistDao: PlaylistDao
) : RepositoryInterface {

    override suspend fun searchYoutube(
        search: String
    ): Flow<PagingData<MappedYoutubeItem>> {
        Log.i("!@#", "Repository call")
        return Pager(config = PagingConfig(20)) {
            YoutubePagingSource("search", youtubeResponseMapper, youtubeApi)
        }.flow
    }

    override fun addToPlaylist(listToAdd: List<MappedYoutubeItem>) {
        Log.i("!@#", "Repository call")
        playlistDao.insertToPlaylist(playlistRoomMapper.mapListToRoomItem(listToAdd))
    }

    override fun deleteFromPlaylist(itemToDelete: MappedYoutubeItem) {
        playlistDao.delete(playlistRoomMapper.mapToEntity(itemToDelete))
    }

    override fun getPlaylist(): Flow<PagingData<MappedYoutubeItem>> =
        Pager(config = PagingConfig(20)) {
            playlistDao.getPlaylist().asPagingSourceFactory(Dispatchers.IO).invoke()
        }.flow.map { pagingData -> pagingData.map { playlistRoomMapper.mapFromEntity(it) } }
}