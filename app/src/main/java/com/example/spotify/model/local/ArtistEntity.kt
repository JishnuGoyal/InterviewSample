package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
class ArtistEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val genres: String,
    val followers: Int,
    val popularity: Int
)