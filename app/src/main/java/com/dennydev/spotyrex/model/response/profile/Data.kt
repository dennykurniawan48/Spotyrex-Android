package com.dennydev.spotyrex.model.response.profile

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val __v: Int,
    val _id: String,
    val email: String,
    val exp: Long,
    val iat: Int,
    val name: String
)