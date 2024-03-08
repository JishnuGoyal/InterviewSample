package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val numberOfTracks: Int,
    val description: String,
    val owner: String
)