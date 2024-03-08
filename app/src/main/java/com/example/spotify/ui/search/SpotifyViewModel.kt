package com.example.spotify.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: Repository,
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
        withContext(Dispatchers.IO) {
            repository.search(query)
        }
    }

}
