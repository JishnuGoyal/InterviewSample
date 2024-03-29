package com.example.spotify.data.api

import com.example.spotify.model.remote.SpotifyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiServices {

    @GET("v1/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = 5,
    ): Response<SpotifyResponse>

}