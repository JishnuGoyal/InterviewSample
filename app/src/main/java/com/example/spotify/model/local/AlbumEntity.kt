package com.example.spotify.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
class AlbumEntity (
    @PrimaryKey
    val id: String,
)