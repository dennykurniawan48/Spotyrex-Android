package com.dennydev.spotyrex.model.response.artist

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val __v: Int,
    val _id: String,
    val artistId: String,
    val duration: Int,
    val image: String,
    val title: String,
    val url: String
)