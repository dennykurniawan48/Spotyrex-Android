package com.dennydev.spotyrex.network.profile

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.model.response.profile.ProfileResponse

interface ApiProfile{
    suspend fun getProfile(token: String): ApiResponse<ProfileResponse>
}