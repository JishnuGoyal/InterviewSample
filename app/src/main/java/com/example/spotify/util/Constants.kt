package com.example.spotify.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64

const val BASE_URL = "https://api.spotify.com/"
const val AUTH_TOKEN_URL = "https://accounts.spotify.com/"
const val clientId = "c6a251e99f4c41febe4857e3c0ced3dc"
const val clientSecret = "8c87b462eed24ab6b6629a3a1b27c998"

const val grantType = "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"


const val toEncode = "$clientId:$clientSecret"
@RequiresApi(Build.VERSION_CODES.O)
val encoded = Base64.getEncoder().encode(toEncode.toByteArray()).toString()
@RequiresApi(Build.VERSION_CODES.O)
val authHeader: String = "Basic $encoded"

