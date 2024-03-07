package com.example.spotify.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spotify.model.remote.AlbumItem
import com.example.spotify.model.remote.ArtistItem
import com.example.spotify.model.remote.PlaylistItem
import com.example.spotify.model.remote.TrackItem

@Dao
interface SpotifyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(tracks: List<TrackItem>)

    @Query("delete from tracks")
    suspend fun deleteTracks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(tracks: List<AlbumItem>)

    @Query("delete from albums")
    suspend fun deleteAlbums()

    // Insert playlists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistItem>)

    // Delete all playlists
    @Query("DELETE FROM playlists")
    suspend fun deletePlaylists()

    // Insert artists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistItem>)

    // Delete all artists
    @Query("DELETE FROM artists")
    suspend fun deleteArtists()
}