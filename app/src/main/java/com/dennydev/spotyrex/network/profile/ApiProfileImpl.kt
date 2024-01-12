package com.dennydev.spotyrex.network.profile

import android.util.Log
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.model.response.login.google.LoginGoogleResponse
import com.dennydev.spotyrex.model.response.profile.ProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.lang.Exception

class ApiProfileImpl(val client: HttpClient): ApiProfile {
    override suspend fun getProfile(token: String): ApiResponse<ProfileResponse> {
        return try{
            val response: ProfileResponse = client.get(Constant.URL_PROFILE){
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }.body()
            ApiResponse.Success(response)
        }catch (e: Exception){
            Log.d("error profile", e.toString())
            ApiResponse.Error("Something wen't wrong")
        }
    }
}