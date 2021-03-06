package com.github.youtube_searcher.repository.retrofit

import com.github.youtube_searcher.Constants.YoutubeSettings.API_KEY
import com.github.youtube_searcher.Constants.YoutubeSettings.MAX_RESULT
import com.github.youtube_searcher.Constants.YoutubeSettings.SNIPPET
import com.github.youtube_searcher.model.fullresponse.YoutubeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("search")
    suspend fun searchVideos(
    @Query("q")search: String,
    @Query("key")apiKey: String = API_KEY,
    @Query("pageToken")pageToken: String?,
    @Query("maxResults")maxResults: String = MAX_RESULT,
    @Query("part")part: String = SNIPPET,

    ):Response<YoutubeSearchResponse>
}