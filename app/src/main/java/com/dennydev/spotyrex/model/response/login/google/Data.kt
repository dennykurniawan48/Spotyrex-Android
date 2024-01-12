package com.dennydev.spotyrex.model.response.login.google

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val _id: String,
    val accessToken: String,
    val email: String,
    val expiresIn: Int,
    val google: Boolean,
    val name: String,
    val __v: Int
)