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
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): PagingSource.LoadResult<String, MappedYoutubeItem> {
        return try {
            Log.i("!@#", "paging source load call")
        //    Log.i("!@#", params.toString())

            val pageIndex = params.key
            val response = youtubeApi.searchVideos(search, API_KEY,pageIndex)
         //   Log.i("!@#", response.body().toString())
            val responseData = mutableListOf<MappedYoutubeItem>()
            val prevKey = response.body()?.prevPageToken
            val nextKey = response.body()?.nextPageToken
            val data = youtubeMapper.mapListFromEntity(response.body()?.items?: emptyList())
            responseData.addAll(data)
          //  Log.i("!@#", responseData.toString())
            PagingSource.LoadResult.Page(responseData,prevKey,nextKey)
        } catch (e: Exception){
            PagingSource.LoadResult.Error(e)
        }
    }
}