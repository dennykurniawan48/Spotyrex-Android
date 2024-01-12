package com.dennydev.spotyrex.model.response.home.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val __v: Int,
    val _id: String,
    val image: String,
    val name: String,
    val songs: List<String>
)