package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
class ArtistEntity (
    @PrimaryKey
    val id: String,
)