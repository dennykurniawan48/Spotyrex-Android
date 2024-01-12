package com.dennydev.spotyrex.model

import com.dennydev.spotyrex.database.Songs

data class PlayerState(
    val playerState: SongState=SongState.STOP,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
    val repeat: Boolean = false,
    val shuffle: Boolean = false,
    val currentAudio: Songs? = null
)

enum class SongState{
    PLAYING,
    PAUSED,
    STOP
}
