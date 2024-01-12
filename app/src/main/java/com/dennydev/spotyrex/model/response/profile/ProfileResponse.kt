package com.dennydev.spotyrex.model.response.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val `data`: Data
)