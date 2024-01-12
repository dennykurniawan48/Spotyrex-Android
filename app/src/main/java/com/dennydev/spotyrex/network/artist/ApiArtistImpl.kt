package com.dennydev.spotyrex.network.artist

import android.util.Log
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.model.response.artist.DetailArtist
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiArtistImpl(private val client: HttpClient): ApiArtist {
    override suspend fun getDetailArtist(id: String): ApiResponse<DetailArtist> {
        return try {
            val response: DetailArtist = client.get(
                "${Constant.URL_ARTIST}/$id"
            ).body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            Log.d("Error get artist", e.toString())
            ApiResponse.Error("Something went wrong.")
        }
    }
}