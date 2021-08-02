package com.github.youtube_searcher.repository

import androidx.paging.PagingData
import com.github.youtube_searcher.R
import com.github.youtube_searcher.di.YoutubeApi
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.YoutubeResponseMapper
import com.github.youtube_searcher.util.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
private val youtubeApi: YoutubeApi,
private val youtubeResponseMapper: YoutubeResponseMapper
):RepositoryInterface {

    override suspend fun searchYoutube(search: String, pageToken: String): Resource<MappedYoutubeItem> {
        return Resource.Empty()
    }
}