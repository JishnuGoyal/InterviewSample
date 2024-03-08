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

    @Query("SELECT * FROM artists")
    fun getAllArtists(): LiveData<List<ArtistEntity>>

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): LiveData<List<PlaylistEntity>>

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): LiveData<List<AlbumEntity>>

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