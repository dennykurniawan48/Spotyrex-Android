package com.dennydev.spotyrex.repository

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.network.login.ApiLoginImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(val client: HttpClient) {
    suspend fun loginGoogle(googleToken: String): ApiResponse<LoginGoogleResponse> {
        return ApiLoginImpl(client).loginWithGoogle(googleToken)
    }
}