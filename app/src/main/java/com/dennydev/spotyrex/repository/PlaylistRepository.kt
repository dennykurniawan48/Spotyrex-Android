package com.dennydev.spotyrex.repository

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.home.playlist.PlaylistResponse
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import com.dennydev.spotyrex.network.home.ApiHomeImpl
import com.dennydev.spotyrex.network.playlist.ApiPlaylistImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class PlaylistRepository @Inject constructor(val client: HttpClient) {
    suspend fun getDetailPlaylist(playlistId: String): ApiResponse<DetailPlaylist> {
        return ApiPlaylistImpl(client).getDetailPlaylist(playlistId)
    }
}