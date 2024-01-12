package com.dennydev.spotyrex.network.login

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse

interface ApiLogin {
    suspend fun loginWithGoogle(token: String): ApiResponse<LoginGoogleResponse>
}