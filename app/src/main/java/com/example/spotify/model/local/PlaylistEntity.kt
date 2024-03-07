package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity (
    @PrimaryKey
    val id: String,
)