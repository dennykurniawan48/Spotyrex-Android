package com.dennydev.spotyrex.repository

import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.artist.DetailArtist
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import com.dennydev.spotyrex.network.artist.ApiArtistImpl
import com.dennydev.spotyrex.network.playlist.ApiPlaylistImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import javax.inject.Inject

@ViewModelScoped
class ArtistRepository @Inject constructor(val client: HttpClient) {
    suspend fun getDetailArtist(artistId: String): ApiResponse<DetailArtist> {
        return ApiArtistImpl(client).getDetailArtist(artistId)
    }
}