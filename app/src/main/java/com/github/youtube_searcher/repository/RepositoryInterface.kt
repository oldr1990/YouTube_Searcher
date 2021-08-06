package com.github.youtube_searcher.repository

import androidx.paging.PagingData
import com.github.youtube_searcher.model.MappedYoutubeItem
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun searchYoutube(
        search: String
    ): Flow<PagingData<MappedYoutubeItem>>
    //Room interact
    suspend fun addToPlaylist(listToAdd: List<MappedYoutubeItem>)
    suspend fun deleteFromPlaylist(itemToDelete: MappedYoutubeItem)
    suspend fun getPlaylist(): Flow<PagingData<MappedYoutubeItem>>
}