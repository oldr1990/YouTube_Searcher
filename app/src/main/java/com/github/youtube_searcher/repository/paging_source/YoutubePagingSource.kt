package com.github.youtube_searcher.repository.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.model.mappers.YoutubeResponseMapper
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import kotlin.Exception

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

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MappedYoutubeItem> {
        return try {
            Log.i("!@#", "paging source load call")
            //    Log.i("!@#", params.toString())
            val pageIndex = params.key
            //   Log.i("!@#", "Request: $search , $pageIndex")
            val response = youtubeApi.searchVideos(search, pageToken = pageIndex)
            //  Log.i("!@#", "response ${response.body().toString()}")
            val responseData = mutableListOf<MappedYoutubeItem>()
            val prevKey = response.body()?.prevPageToken
            val nextKey = response.body()?.nextPageToken
            if (response.isSuccessful) {
                val data = youtubeMapper.mapListFromEntity(response.body()?.items ?: emptyList())
                responseData.addAll(data)
                //   Log.i("!@#", "ResponseData: $responseData")
                LoadResult.Page(responseData, prevKey, nextKey)
            } else {
                Log.i("!@#", "Проверь квоты по ютуб апи!}")
                LoadResult.Error(
                    Exception(
                        response.body()?.error?.message ?: "Unexpected Error"
                    )
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}