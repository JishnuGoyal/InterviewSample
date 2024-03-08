package com.example.spotify.data.api

import com.example.spotify.model.remote.TokenResponse
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Authentication {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/token")
    suspend fun getAuthToken(
        @Body body: RequestBody,
    ): Response<TokenResponse>
}

