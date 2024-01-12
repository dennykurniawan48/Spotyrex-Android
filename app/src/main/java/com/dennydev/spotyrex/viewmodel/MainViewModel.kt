package com.dennydev.spotyrex.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.spotyrex.database.Songs
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.home.playlist.PlaylistResponse
import com.dennydev.spotyrex.model.response.playlist.Data as Playlist
import com.dennydev.spotyrex.model.response.artist.Data as ArtistSongs
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.repository.AuthStoreRepository
import com.dennydev.spotyrex.repository.MainRepository
import com.dennydev.spotyrex.service.SpotyrexMediaController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val datastoreRepository: AuthStoreRepository,
    private val repository: MainRepository,
    private val spotyrexMediaController: SpotyrexMediaController
): ViewModel() {
    val token = datastoreRepository.flowToken
    private val _selectedBottomNav: MutableState<String> = mutableStateOf(Screen.HomeScreen.route)
    val selecteBottomNav: State<String> = _selectedBottomNav
    private val _homePlaylist = mutableStateOf<ApiResponse<PlaylistResponse>>(ApiResponse.Idle())
    val homePlaylist:State<ApiResponse<PlaylistResponse>> = _homePlaylist

    val playerState = spotyrexMediaController.flowPlayer

    fun onChangeSelectedBottomNav(route: String){
        _selectedBottomNav.value = route
    }

    fun skipNext(){
        spotyrexMediaController.skipNext()
    }

    fun skipPrev(){
        spotyrexMediaController.skipPrevious()
    }

    fun resume(){
        spotyrexMediaController.resume()
    }

    fun setRepeatEnabled(repeat: Boolean){
        spotyrexMediaController.setRepeatModeEnabled(repeat)
    }

    fun setShuffleEnabled(shuffle: Boolean){
        spotyrexMediaController.setShuffleModeEnabled(shuffle)
    }

    fun pause(){
        spotyrexMediaController.pause()
    }

    fun getPlaylist(){
        _homePlaylist.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _homePlaylist.value = repository.getPlaylist()
        }
    }


    fun play(indexMedia: Int){
        spotyrexMediaController.play(indexMedia)
    }

    fun getCurrentPosition() = spotyrexMediaController.getCurrentMediaPosition()

    fun convertPlaylistResponseToPlaylist(data: Playlist, autoPlay: Boolean, indexMedia: Int = 0){
        val listSongs = data.songs.map{
            Songs(
                id = 0,
                title = it.title,
                artist = it.artistId.name,
                artistId = it.artistId._id,
                thumbnail = it.image,
                url = it.url,
                duration = it.duration*1000
            )
        }

        spotyrexMediaController.addMediaItems(listSongs)

        if(autoPlay){
            play(indexMedia)
        }
    }

    fun convertArtistResponseToPlaylist(data: ArtistSongs, autoPlay: Boolean, indexMedia: Int = 0){
        val listSongs = data.songs.map{
            Songs(
                id = 0,
                title = it.title,
                artist = data.name,
                artistId = data._id,
                thumbnail = it.image,
                url = it.url,
                duration = it.duration*1000
            )
        }

        spotyrexMediaController.addMediaItems(listSongs)

        if(autoPlay){
            play(indexMedia)
        }
    }

    override fun onCleared() {
        spotyrexMediaController.destroy()
        super.onCleared()
    }
}