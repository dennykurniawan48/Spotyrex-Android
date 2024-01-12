package com.dennydev.spotyrex.model.response.login.google

import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleResponse(
    val `data`: Data
)