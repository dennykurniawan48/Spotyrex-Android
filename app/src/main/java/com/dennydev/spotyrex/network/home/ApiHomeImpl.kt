package com.dennydev.spotyrex.network.home

import android.util.Log
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.model.response.home.playlist.PlaylistResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiHomeImpl(val client: HttpClient): ApiHome {
    override suspend fun getPlaylist(): ApiResponse<PlaylistResponse> {
        return try {
            val response: PlaylistResponse = client.get(
                Constant.URL_PLAYLIST
            ).body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            Log.d("Error login", e.toString())
            ApiResponse.Error("Something went wrong.")
        }
    }
}