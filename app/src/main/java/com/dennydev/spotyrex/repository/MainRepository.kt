package com.dennydev.spotyrex.repository

import com.dennydev.spotyrex.database.Songs
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.home.playlist.PlaylistResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.network.home.ApiHomeImpl
import com.dennydev.spotyrex.network.login.ApiLoginImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor(val client: HttpClient) {
    suspend fun getPlaylist(): ApiResponse<PlaylistResponse> {
        return ApiHomeImpl(client).getPlaylist()
    }
}