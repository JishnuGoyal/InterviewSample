package com.example.spotify.data

import android.content.Context
import com.example.spotify.model.remote.TokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val sharedPreferenceName = "SharedPreferences"
private const val authTokenPreference = "authToken"
private const val authTimeout = "timeout"

@Singleton
class SpotifySharedPreferences @Inject constructor(@ApplicationContext context: Context) {
    val prefs = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)

    fun getAuthToken(): String? {
        if (System.currentTimeMillis() >= getAuthTimeout()) {
            return null
        }
        return prefs.getString(authTokenPreference, null)
    }

    fun setAuthToken(tokenResponse: TokenResponse) {
        prefs.edit().putString(authTokenPreference, tokenResponse.accessToken).apply()
        prefs.edit().putLong(authTimeout, tokenResponse.expiresIn.toLong() + System.currentTimeMillis()).apply()
    }

    private fun getAuthTimeout(): Long {
        return prefs.getLong(authTimeout, 0L)
    }
}