package com.dennydev.spotyrex.network.login

import android.util.Log
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiLoginImpl(val client: HttpClient): ApiLogin {
    override suspend fun loginWithGoogle(token: String): ApiResponse<LoginGoogleResponse> {
        return try {
            val response: LoginGoogleResponse = client.get(
                Constant.URL_LOGIN
                    .replace("{token}", token)
            ).body()
            ApiResponse.Success(response)
        }catch(e: Exception){
            Log.d("Error login", e.toString())
            ApiResponse.Error("Something went wrong.")
        }
    }
}