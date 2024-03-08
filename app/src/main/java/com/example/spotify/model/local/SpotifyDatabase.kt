package com.example.spotify.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spotify.data.SpotifyDao

@Database(entities = [TrackEntity::class, ArtistEntity::class, PlaylistEntity::class, AlbumEntity::class], version = 2)
abstract class SpotifyDatabase: RoomDatabase() {
    abstract fun spotifyDao(): SpotifyDao
}