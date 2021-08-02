package com.github.youtube_searcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ActivityComponent::class)
object MainModule {
    @ActivityRetainedScoped
    @Provides
    fun provideOkHTTP(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .callTimeout(20, TimeUnit.SECONDS)
            .build()

    @ActivityRetainedScoped
    @Provides
    fun provideYoutubeApi(okHTTP: OkHttpClient): YoutubeApi = Retrofit.Builder()
        .baseUrl("https://youtube.googleapis.com/youtube/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHTTP)
        .build()
        .create(YoutubeApi::class.java)
}