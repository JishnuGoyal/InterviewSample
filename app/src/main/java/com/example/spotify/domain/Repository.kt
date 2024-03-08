package com.example.spotify.domain

import com.example.spotify.data.SpotifyDao
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(private val dao: SpotifyDao) {

    suspend fun insertTracks(tracks: List<TrackEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertTrack(tracks)
        }
    }

    suspend fun insertAlbums(albums: List<AlbumEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAlbum(albums)
        }
    }

    suspend fun insertArtists(artists: List<ArtistEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertArtists(artists)
        }
    }

    suspend fun insertPlaylists(playlists: List<PlaylistEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertPlaylists(playlists)
        }
    }

    fun getTracks() = dao.getAllTracks()
    fun getAlbums() = dao.getAllAlbums()
    fun getPlaylists() = dao.getAllPlaylists()
    fun getArtists() = dao.getAllArtists()


    suspend fun deleteTracks() = dao.deleteTracks()
    suspend fun deleteAlbums() = dao.deleteAlbums()
    suspend fun deletePlaylists() = dao.deletePlaylists()
    suspend fun deleteArtists() = dao.deleteArtists()

}