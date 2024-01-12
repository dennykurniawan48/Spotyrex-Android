package com.dennydev.spotyrex.model.response.artist

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val __v: Int,
    val _id: String,
    val desc: String,
    val image: String,
    val name: String,
    val songs: List<Song>
)