package com.dennydev.spotyrex.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.repository.AuthStoreRepository
import com.dennydev.spotyrex.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val datastoreRepository: AuthStoreRepository,
    private val repository: LoginRepository
): ViewModel() {
    val token = datastoreRepository.flowToken
    private val _isGoogleSignIn: MutableState<Boolean> = mutableStateOf(false)
    val isGoogleSignIn: State<Boolean> = _isGoogleSignIn

    private val _loginResponse: MutableState<ApiResponse<LoginGoogleResponse>> = mutableStateOf(ApiResponse.Idle())
    val loginResponse: State<ApiResponse<LoginGoogleResponse>> = _loginResponse

    fun onChangeIsGoogleLogin(status: Boolean){
        _isGoogleSignIn.value = status
    }

    fun loginWithGoogle(token: String){
        _loginResponse.value = ApiResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.loginGoogle(token)
            if(response is ApiResponse.Success){
                response.data?.data?.let {
                    datastoreRepository.saveToken(it.accessToken, it.expiresIn)
                }
            }
            _loginResponse.value = response
        }
    }

}