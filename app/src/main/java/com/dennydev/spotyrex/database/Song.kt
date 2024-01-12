package com.dennydev.spotyrex.database

import androidx.media3.common.MediaItem
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "playlist")
data class Songs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val artist: String,
    val artistId: String,
    val thumbnail: String,
    val url: String,
    val duration:Int
)

fun MediaItem.toSong() =
    Songs(id = 0, title = mediaMetadata.title.toString(), artist = mediaMetadata.artist.toString(), artistId = mediaMetadata.station.toString(), thumbnail = mediaMetadata.artworkUri.toString(), url = mediaId, duration = mediaMetadata.discNumber ?: 0 )