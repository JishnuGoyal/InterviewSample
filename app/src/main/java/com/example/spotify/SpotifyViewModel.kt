package com.example.spotify

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotify.api.ApiServices
import com.example.spotify.data.SpotifySharedPreferences
import com.example.spotify.domain.AuthenticationRepository
import com.example.spotify.domain.Repository
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity
import com.example.spotify.model.remote.SpotifyResponse
import com.example.spotify.model.remote.TrackItem
import com.example.spotify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val apiServices: ApiServices,
    private val authenticationRepository: AuthenticationRepository,
    private val repository: Repository,
    private val sharedPreferences: SpotifySharedPreferences
) : ViewModel() {

    val searchResult: MutableLiveData<Resource<SpotifyResponse>> = MutableLiveData()
    val trackLiveData = repository.getTracks()
    val artistLiveData = repository.getArtists()
    val playlistLiveData = repository.getPlaylists()
    val albumLiveData = repository.getAlbums()

    // state variables
    var currentTrack: TrackEntity? = null

    var currentAlbum: AlbumEntity? = null

    var currentPlaylist: PlaylistEntity? = null

    var currentArtist: ArtistEntity? = null

    fun search(query: String) = viewModelScope.launch {
        searchResult.postValue(Resource.Loading())
        val token = "Bearer " + getToken()
        val response = apiServices.search(query, "album,playlist,artist,track", token)

        if (response.isSuccessful) {
            response.body()?.let { searchResponse ->
                repository.deleteTracks()
                repository.deleteAlbums()
                repository.deletePlaylists()
                repository.deleteArtists()

                repository.insertTracks(
                    searchResponse.tracks.items.map {
                        it.toEntity()
                    }.toList()
                )
                repository.insertAlbums(
                    searchResponse.albums.items.map {
                        it.toEntity()
                    }.toList()
                )

                repository.insertPlaylists(
                    searchResponse.playlists.items.map {
                        it.toEntity()
                    }.toList()
                )

                repository.insertArtists(
                    searchResponse.artists.items.map {
                        it.toEntity()
                    }.toList()
                )


                return@launch searchResult.postValue(Resource.Success(searchResponse))
            }
        }
        return@launch searchResult.postValue(Resource.Error(response.message()))
    }


    suspend fun getToken(): String {
        sharedPreferences.getAuthToken()?.let {
            return it
        }

        Log.d("spotify", "fetching new token")
        val response = authenticationRepository.getToken()
        if (response.isSuccessful) {
            response.body()?.let { tokenResponse ->
                sharedPreferences.setAuthToken(tokenResponse)
                return tokenResponse.accessToken
            }
        }

        // get-token failed. Update UI accordingly.
        return ""
    }
}
