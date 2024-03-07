package com.example.spotify.domain

import com.example.spotify.data.SpotifyDao
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

    suspend fun getTracks() = dao.getAllTracks()

    suspend fun deleteTracks() = dao.deleteTracks()

}