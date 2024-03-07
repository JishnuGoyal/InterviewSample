package com.example.spotify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotify.api.ApiServices
import com.example.spotify.data.SpotifySharedPreferences
import com.example.spotify.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val apiServices: ApiServices,
    private val authenticationRepository: AuthenticationRepository,
    private val sharedPreferences: SpotifySharedPreferences
) : ViewModel() {


    fun search() = viewModelScope.launch {
        val token = "Bearer " + getToken()
        val response = apiServices.search("rihanna", "album", token)

        if (response.isSuccessful) {
            response.body()?.let { tokenResponse ->

               Log.d("??success", tokenResponse.toString())
            }
        } else {
            Log.d("??failure", response.toString())
        }
    }



    suspend fun getToken(): String {
        sharedPreferences.getAuthToken()?.let {
            return it
        }

        Log.d("spotify", "fetching new token")
        val response = authenticationRepository.getToken()
        if (response.isSuccessful) {
            response.body()?.let { tokenResponse ->
                sharedPreferences.setAuthToken(tokenResponse)
                return tokenResponse.accessToken
            }
        }

        // get-token failed. Update UI accordingly.
        return ""
    }




}