package com.github.youtube_searcher.di


import com.github.youtube_searcher.model.mappers.PlaylistRoomMapper
import com.github.youtube_searcher.model.mappers.YoutubeResponseMapper
import com.github.youtube_searcher.repository.DefaultRepository
import com.github.youtube_searcher.repository.RepositoryInterface
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import com.github.youtube_searcher.repository.room.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideRepository(
        youtubeApi: YoutubeApi,
        youtubeResponseMapper: YoutubeResponseMapper,
        playlistRoomMapper: PlaylistRoomMapper,
        playlistDao: PlaylistDao
    ): RepositoryInterface =
        DefaultRepository(
            youtubeApi,
            youtubeResponseMapper,
            playlistRoomMapper,
           playlistDao
        )

    @Singleton
    @Provides
    fun provideOkHTTP(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .callTimeout(20, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideYoutubeApi(okHTTP: OkHttpClient): YoutubeApi = Retrofit.Builder()
        .baseUrl("https://youtube.googleapis.com/youtube/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHTTP)
        .build()
        .create(YoutubeApi::class.java)
}