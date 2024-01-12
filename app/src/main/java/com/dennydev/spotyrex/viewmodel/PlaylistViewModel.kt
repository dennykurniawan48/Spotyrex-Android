package com.dennydev.spotyrex.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.playlist.DetailPlaylist
import com.dennydev.spotyrex.repository.AuthStoreRepository
import com.dennydev.spotyrex.repository.MainRepository
import com.dennydev.spotyrex.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val repository: PlaylistRepository
): ViewModel() {
    private val _playlistResponse = mutableStateOf<ApiResponse<DetailPlaylist>>(ApiResponse.Idle())
    val playlistResponse: State<ApiResponse<DetailPlaylist>> = _playlistResponse

    fun getDetailPlaylist(playlistId: String){
        _playlistResponse.value = ApiResponse.Loading()
        viewModelScope.launch {
            _playlistResponse.value = repository.getDetailPlaylist(playlistId)
        }
    }
}