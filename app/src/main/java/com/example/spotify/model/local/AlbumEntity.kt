package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
class AlbumEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val artists: String,
    val releaseDate: String
)