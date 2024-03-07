package com.example.spotify.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity

@Dao
interface SpotifyDao {
    @Query("SELECT * FROM tracks")
    fun getAllTracks(): LiveData<List<TrackEntity>>

    // Get all albums
    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>

    // Get all playlists
    @Query("SELECT * FROM playlists")
    suspend fun getAllPlaylists(): List<PlaylistEntity>

    // Get all artists
    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(tracks: List<TrackEntity>)

    @Query("delete from tracks")
    suspend fun deleteTracks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albums: List<AlbumEntity>)

    @Query("delete from albums")
    suspend fun deleteAlbums()

    // Insert playlists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>)

    // Delete all playlists
    @Query("DELETE FROM playlists")
    suspend fun deletePlaylists()

    // Insert artists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    // Delete all artists
    @Query("DELETE FROM artists")
    suspend fun deleteArtists()
}