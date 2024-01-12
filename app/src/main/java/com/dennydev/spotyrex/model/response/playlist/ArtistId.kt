package com.dennydev.spotyrex.model.response.playlist

import kotlinx.serialization.Serializable

@Serializable
data class ArtistId(
    val __v: Int,
    val _id: String,
    val desc: String,
    val image: String,
    val name: String
)