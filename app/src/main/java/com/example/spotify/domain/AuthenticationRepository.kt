package com.example.spotify.domain

import com.example.spotify.api.Authentication
import com.example.spotify.model.remote.TokenResponse
import com.example.spotify.util.grantType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val authentication: Authentication) {

    suspend fun getToken(): Response<TokenResponse> {
        val requestBody = grantType.toRequestBody()
        return authentication.getAuthToken(body = requestBody)
    }
}