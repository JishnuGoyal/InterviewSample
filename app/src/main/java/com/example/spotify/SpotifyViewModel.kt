package com.example.spotify

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotify.data.SpotifySharedPreferences
import com.example.spotify.domain.AuthenticationRepository
import com.example.spotify.domain.Repository
import com.example.spotify.model.local.AlbumEntity
import com.example.spotify.model.local.ArtistEntity
import com.example.spotify.model.local.PlaylistEntity
import com.example.spotify.model.local.TrackEntity
import com.example.spotify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val repository: Repository,
    private val sharedPreferences: SpotifySharedPreferences
) : ViewModel() {

    val searchResult: LiveData<Resource<String>>
        get() = repository.searchResult
    val trackLiveData: LiveData<List<TrackEntity>> = repository.getTracks()
    val artistLiveData: LiveData<List<ArtistEntity>> = repository.getArtists()
    val playlistLiveData: LiveData<List<PlaylistEntity>> = repository.getPlaylists()
    val albumLiveData: LiveData<List<AlbumEntity>> = repository.getAlbums()

    // state variables
    var currentTrack: TrackEntity? = null

    var currentAlbum: AlbumEntity? = null

    var currentPlaylist: PlaylistEntity? = null

    var currentArtist: ArtistEntity? = null

    fun search(query: String) = viewModelScope.launch {
        val token = "Bearer " + getToken()
        withContext(Dispatchers.IO) {
            repository.search(query, token)
        }
    }


    suspend fun getToken(): String {
        sharedPreferences.getAuthToken()?.let {
            return it
        }
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
