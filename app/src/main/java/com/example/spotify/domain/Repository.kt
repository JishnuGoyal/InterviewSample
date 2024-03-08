package com.example.spotify.domain

import androidx.lifecycle.MutableLiveData
import com.example.spotify.data.api.ApiServices
import com.example.spotify.data.SpotifyDao
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity
import com.example.spotify.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: SpotifyDao,
    private val apiServices: ApiServices,
    private val authenticationRepository: AuthenticationRepository
) {

    val searchResult: MutableLiveData<Resource<String>> = MutableLiveData()
    suspend fun search(query: String) {
        val tokenResponse = authenticationRepository.getToken()
        if (tokenResponse == "") {
            searchResult.postValue(Resource.Error("Couldn't fetch auth token. Check Internet."))
            return
        }
        val token = "Bearer " + tokenResponse

        searchResult.postValue(Resource.Loading())
        try {
            val response = apiServices.search(query, "album,playlist,artist,track", token)
            if (response.isSuccessful) {
                searchResult.postValue(Resource.Success(""))
                response.body()?.let { searchResponse ->
                    deleteTracks()
                    deleteAlbums()
                    deletePlaylists()
                    deleteArtists()

                    insertTracks(
                        searchResponse.tracks.items.map {
                            it.toEntity()
                        }.toList()
                    )
                    insertAlbums(
                        searchResponse.albums.items.map {
                            it.toEntity()
                        }.toList()
                    )

                    insertPlaylists(
                        searchResponse.playlists.items.map {
                            it.toEntity()
                        }.toList()
                    )

                    insertArtists(
                        searchResponse.artists.items.map {
                            it.toEntity()
                        }.toList()
                    )

                }
            } else {
                searchResult.postValue(Resource.Error("Couldn't find results"))
            }
        } catch (e: Exception) {
            searchResult.postValue(Resource.Error("Couldn't find results. Check Internet."))
        }
    }

    private suspend fun insertTracks(tracks: List<TrackEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertTrack(tracks)
        }
    }

    private suspend fun insertAlbums(albums: List<AlbumEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAlbum(albums)
        }
    }

    private suspend fun insertArtists(artists: List<ArtistEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertArtists(artists)
        }
    }

    private suspend fun insertPlaylists(playlists: List<PlaylistEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertPlaylists(playlists)
        }
    }

    fun getTracks() = dao.getAllTracks()
    fun getAlbums() = dao.getAllAlbums()
    fun getPlaylists() = dao.getAllPlaylists()
    fun getArtists() = dao.getAllArtists()


    private suspend fun deleteTracks() = dao.deleteTracks()
    private suspend fun deleteAlbums() = dao.deleteAlbums()
    private suspend fun deletePlaylists() = dao.deletePlaylists()
    private suspend fun deleteArtists() = dao.deleteArtists()

}