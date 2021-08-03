package com.github.youtube_searcher.di

import android.content.Context
import androidx.room.Room
import com.github.youtube_searcher.model.PlaylistRoomMapper
import com.github.youtube_searcher.model.YoutubeResponseMapper
import com.github.youtube_searcher.repository.DefaultRepository
import com.github.youtube_searcher.repository.RepositoryInterface
import com.github.youtube_searcher.repository.retrofit.YoutubeApi
import com.github.youtube_searcher.repository.room.PlaylistDao
import com.github.youtube_searcher.repository.room.PlaylistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
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
    fun providePlaylistDatabase(@ApplicationContext context: Context): PlaylistDatabase =
        Room.databaseBuilder(context,
            PlaylistDatabase::class.java,
            "youtube_database").build()

    @Singleton
    @Provides
    fun providePlaylistDao(db: PlaylistDatabase):PlaylistDao = db.playlistDao()

    @Singleton
    @Provides
    fun provideRepository(
        youtubeApi: YoutubeApi,
        youtubeResponseMapper: YoutubeResponseMapper,
        playlistRoomMapper: PlaylistRoomMapper,
      //  playlistDao: PlaylistDao
    ): RepositoryInterface =
        DefaultRepository(
            youtubeApi,
            youtubeResponseMapper,
            playlistRoomMapper,
          //  playlistDao
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