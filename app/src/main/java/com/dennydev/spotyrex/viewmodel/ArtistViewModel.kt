package com.dennydev.spotyrex.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.artist.DetailArtist
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import com.dennydev.spotyrex.repository.ArtistRepository
import com.dennydev.spotyrex.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistRepository
): ViewModel() {
    private val _artistResponse = mutableStateOf<ApiResponse<DetailArtist>>(ApiResponse.Idle())
    val artistResponse: State<ApiResponse<DetailArtist>> = _artistResponse

    fun getDetaiArtist(artistId: String){
        _artistResponse.value = ApiResponse.Loading()
        viewModelScope.launch {
            _artistResponse.value = repository.getDetailArtist(artistId)
        }
    }
}