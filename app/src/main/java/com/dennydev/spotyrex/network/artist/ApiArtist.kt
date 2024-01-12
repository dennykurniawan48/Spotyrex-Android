package com.dennydev.spotyrex.network.artist

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.artist.DetailArtist

interface ApiArtist {
    suspend fun getDetailArtist(id: String): ApiResponse<DetailArtist>
}