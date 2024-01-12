package com.dennydev.spotyrex.repository

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.profile.ProfileResponse
import com.dennydev.spotyrex.network.profile.ApiProfileImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class ProfileRepository @Inject constructor(val client: HttpClient) {
    suspend fun getProfile(token: String): ApiResponse<ProfileResponse> = ApiProfileImpl(client).getProfile(token)
}