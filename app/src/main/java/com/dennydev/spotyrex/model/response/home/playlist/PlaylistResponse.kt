package com.dennydev.spotyrex.model.response.home.playlist

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    val `data`: List<Data>
)