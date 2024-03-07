package com.example.spotify.model.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.spotify.data.SpotifyDao
import com.example.spotify.model.remote.AlbumItem
import com.example.spotify.model.remote.ArtistItem
import com.example.spotify.model.remote.PlaylistItem
import com.example.spotify.model.remote.TrackItem

@Database(entities = [TrackItem::class, ArtistItem::class, PlaylistItem::class, AlbumItem::class], version = 1)
abstract class SpotifyDatabase: RoomDatabase() {
    abstract fun spotifyDao(): SpotifyDao
}