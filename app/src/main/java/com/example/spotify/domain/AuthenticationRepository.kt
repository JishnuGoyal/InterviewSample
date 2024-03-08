package com.example.spotify.domain

import android.util.Log
import com.example.spotify.data.api.Authentication
import com.example.spotify.data.SpotifySharedPreferences
import com.example.spotify.model.remote.TokenResponse
import com.example.spotify.util.grantType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authentication: Authentication,
    private val sharedPreferences: SpotifySharedPreferences
) {

    suspend fun getToken(): String {
        sharedPreferences.getAuthToken()?.let {
            Log.d("spotify", "use old token")
            return it
        }
        try {
            val response = getTokenFromApi()
            if (response.isSuccessful) {
                Log.d("spotify", "fetch new token")
                response.body()?.let { tokenResponse ->
                    sharedPreferences.setAuthToken(tokenResponse)
                    return tokenResponse.accessToken
                }
            }
        } catch (e: Exception) {
        }
        return ""
    }

    suspend fun getTokenFromApi(): Response<TokenResponse> {
        val requestBody = grantType.toRequestBody()
        return authentication.getAuthToken(body = requestBody)
    }
}