package com.dennydev.spotyrex.network.playlist

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist

interface ApiPlaylist {
    suspend fun getDetailPlaylist(playlistId: String): ApiResponse<DetailPlaylist>
}