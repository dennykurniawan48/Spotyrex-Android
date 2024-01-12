package com.dennydev.spotyrex.network.playlist

import android.util.Log
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiPlaylistImpl(val client: HttpClient): ApiPlaylist {
    override suspend fun getDetailPlaylist(playlistId: String): ApiResponse<DetailPlaylist> {
        return try {
            val response: DetailPlaylist = client.get(
                "${Constant.URL_PLAYLIST}/$playlistId"
            ).body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            Log.d("Error get playlist", e.toString())
            ApiResponse.Error("Something went wrong.")
        }
    }
}