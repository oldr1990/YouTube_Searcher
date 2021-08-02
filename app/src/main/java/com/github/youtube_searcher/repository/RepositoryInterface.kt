package com.github.youtube_searcher.repository

import androidx.paging.PagingData
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.util.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun searchYoutube(
        search: String,
        pageToken: String
        ): Resource<MappedYoutubeItem>
}