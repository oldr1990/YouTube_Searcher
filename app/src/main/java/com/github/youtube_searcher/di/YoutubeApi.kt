package com.github.youtube_searcher.di

import com.github.youtube_searcher.model.fullresponse.YoutubeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("/search?")
    suspend fun searchVideos(
    @Query("q")search: String,
    @Query("key")apiKey: String,
    @Query("pageToken")pageToken: String,
    @Query("maxResults")maxResults: String
    ):Response<YoutubeSearchResponse>
}