package com.dennydev.spotyrex.model.response.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val __v: Int,
    val _id: String,
    val artistId: ArtistId,
    val duration: Int,
    val image: String,
    val title: String,
    val url: String
)