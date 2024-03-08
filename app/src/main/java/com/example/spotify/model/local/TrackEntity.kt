package com.example.spotify.model.local

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val popularity: Int,
    val artists: String
)