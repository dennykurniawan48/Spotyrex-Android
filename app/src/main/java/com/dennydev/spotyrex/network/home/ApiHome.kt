package com.dennydev.spotyrex.network.home

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.home.playlist.PlaylistResponse

interface ApiHome {
    suspend fun getPlaylist(): ApiResponse<PlaylistResponse>
}