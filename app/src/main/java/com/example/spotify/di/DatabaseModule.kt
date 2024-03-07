package com.example.spotify.di

import android.content.Context
import androidx.room.Room
import com.example.spotify.data.SpotifyDao
import com.example.spotify.model.local.SpotifyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SpotifyDatabase {
        return Room.databaseBuilder(
            context,
            SpotifyDatabase::class.java,
            "spotify_database"
        ).build()
    }

    @Provides
    fun provideSpotifyDao(database: SpotifyDatabase): SpotifyDao {
        return database.spotifyDao()
    }
}