package com.github.youtube_searcher.di

import android.content.Context
import androidx.room.Room
import com.github.youtube_searcher.repository.room.PlaylistDao
import com.github.youtube_searcher.repository.room.PlaylistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityComponent::class)
object RoomModule {
    @ActivityRetainedScoped
    @Provides
    fun providePlaylistDatabase(@ApplicationContext context: Context): PlaylistDatabase =
        Room.databaseBuilder(context,
        PlaylistDatabase::class.java,
        "youtube_database").build()

    @ActivityRetainedScoped
    @Provides
    fun providePlaylistDao(db: PlaylistDatabase):PlaylistDao = db.playlistDao()
}