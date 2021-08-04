package com.github.youtube_searcher.repository.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.youtube_searcher.Constants.YoutubeSettings.API_KEY
import com.github.youtube_searcher.Constants.YoutubeSettings.MAX_RESULT
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.YoutubeResponseMapper
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import java.lang.Exception
import javax.inject.Inject

class YoutubePagingSource
    (
    private val search: String,
    private val youtubeMapper: YoutubeResponseMapper,
    private val youtubeApi: YoutubeApi
) : PagingSource<String, MappedYoutubeItem>() {


    override fun getRefreshKey(state: PagingState<String, MappedYoutubeItem>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            Log.i("!@#", "paging source get refresh key ca;l;")
            val anchorPage = state.closestPageToPosition(anchorPosition)
            if (anchorPage?.prevKey != null) anchorPage.nextKey
            else null
        }
    }

    override suspend fun load(params: LoadParams<String>): PagingSource.LoadResult<String, MappedYoutubeItem> {
        return try {
            Log.i("!@#", "paging source load call")
        //    Log.i("!@#", params.toString())
            val pageIndex = params.key
            Log.i("!@#", "Request: $search , $pageIndex")
            val response = youtubeApi.searchVideos(search,pageToken = pageIndex)
           Log.i("!@#", "response ${response.body().toString()}")
            val responseData = mutableListOf<MappedYoutubeItem>()
            val prevKey = response.body()?.prevPageToken
            val nextKey = response.body()?.nextPageToken
            val data = youtubeMapper.mapListFromEntity(response.body()?.items?: emptyList())
            responseData.addAll(data)
           Log.i("!@#", "ResponseData: $responseData")
            PagingSource.LoadResult.Page(responseData,prevKey,nextKey)
        } catch (e: Exception){
            PagingSource.LoadResult.Error(e)
        }
    }
}