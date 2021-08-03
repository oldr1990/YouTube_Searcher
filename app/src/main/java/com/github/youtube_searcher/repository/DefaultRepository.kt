package com.github.youtube_searcher.repository

import androidx.paging.PagingData
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.PlaylistRoomMapper
import com.github.youtube_searcher.model.YoutubeResponseMapper
import com.github.youtube_searcher.repository.room.PlaylistDao
import com.github.youtube_searcher.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val youtubeApi: YoutubeApi,
    private val youtubeResponseMapper: YoutubeResponseMapper,
    private val playlistRoomMapper: PlaylistRoomMapper,
    private val playlistDao: PlaylistDao
) : RepositoryInterface {

    override suspend fun searchYoutube(
        search: String,
        pageToken: String
    ): Flow<PagingData<MappedYoutubeItem>> =
        flow { }

    override fun addToPlaylist(listToAdd: List<MappedYoutubeItem>) {
        playlistDao.insertToPlaylist(playlistRoomMapper.mapListToRoomItem(listToAdd))
    }
    override fun deleteFromPlaylist(itemToDelete: MappedYoutubeItem) {
        playlistDao.delete(playlistRoomMapper.mapToEntity(itemToDelete))
    }

    override fun getPlaylist(): Flow<PagingData<MappedYoutubeItem>> =
        flow { }
}