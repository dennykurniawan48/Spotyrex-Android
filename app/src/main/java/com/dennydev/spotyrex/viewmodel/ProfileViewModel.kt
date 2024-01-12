package com.dennydev.spotyrex.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.model.response.profile.ProfileResponse
import com.dennydev.spotyrex.repository.AuthStoreRepository
import com.dennydev.spotyrex.repository.PlaylistRepository
import com.dennydev.spotyrex.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val authRepository: AuthStoreRepository
): ViewModel() {
    val token = authRepository.flowToken
    private val _profileResponse = mutableStateOf<ApiResponse<ProfileResponse>>(ApiResponse.Idle())
    val profileResponse: State<ApiResponse<ProfileResponse>> = _profileResponse

    fun getProfile(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            _profileResponse.value = repository.getProfile(token)
        }
    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.removeToken()
        }
    }
}