package com.dennydev.spotyrex.service

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Player.RepeatMode
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.dennydev.spotyrex.database.Songs
import com.dennydev.spotyrex.database.toSong
import com.dennydev.spotyrex.model.PlayerState
import com.dennydev.spotyrex.model.SongState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class SpotyrexMediaController @Inject constructor(private val context: Context) {
    private var controllerFuture: ListenableFuture<MediaController>
    private val mediaController: MediaController? get() = if(controllerFuture.isDone) controllerFuture.get() else null

    var flowPlayer = MutableStateFlow(PlayerState())
    val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.Main+job)

    init {
        Log.e("Craeting service token", ComponentName(context, SpotyrexAudioService::class.java).className)
        val sessionToken = SessionToken(context, ComponentName(context, SpotyrexAudioService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

        controllerFuture.addListener({
            mediaController?.addListener(object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    with(player) {
                        coroutineScope.launch {
                            Log.d("Current item", currentMediaItem?.toSong().toString())
                            flowPlayer.emit(
                                PlayerState(
                                    playerState = playbackState.run {
                                        return@run when (this) {
                                            Player.STATE_IDLE -> SongState.STOP
                                            Player.STATE_ENDED -> SongState.STOP
                                            else -> if (isPlaying) SongState.PLAYING else SongState.PAUSED
                                        }
                                    }, isPlaying = isPlaying,
                                    repeat = repeatMode == Player.REPEAT_MODE_ALL,
                                    shuffle = shuffleModeEnabled,
                                    currentAudio = currentMediaItem?.toSong(),
                                    currentPosition = currentPosition
                                )
                            )
                        }
                    }
                }
            })
        }, MoreExecutors.directExecutor())
    }


    fun addMediaItems(songs: List<Songs>){
        songs.map { audio ->
            MediaItem.Builder()
                .setMediaId(audio.url)
                .setUri(audio.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setArtist(audio.artist)
                        .setTitle(audio.title)
                        .setSubtitle(audio.artist)
                        .setStation(audio.artistId)
                        .setDiscNumber(audio.duration)
                        .setArtworkUri(Uri.parse(audio.thumbnail))
                        .build()
                )
                .build()
        }.also {
            mediaController?.setMediaItems(it)
        }
    }

    fun play(mediaItemIndex: Int){
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }

    fun resume(){
        mediaController?.play()
    }

    fun pause(){
        mediaController?.pause()
    }

    fun skipNext(){
        mediaController?.seekToNext()
    }

    fun skipPrevious(){
        mediaController?.seekToPrevious()
    }

    fun setShuffleModeEnabled(shuffleMode: Boolean){
        mediaController?.shuffleModeEnabled = shuffleMode
    }

    fun setRepeatModeEnabled(repeatMode: Boolean){
        mediaController?.repeatMode = if(repeatMode) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
    }

    fun getCurrentMediaPosition() {
        coroutineScope.launch {
            flowPlayer.emit(flowPlayer.value.copy(currentPosition = mediaController?.currentPosition ?: 0L))
        }
    }

    fun destroy(){
        coroutineScope.cancel()
        MediaController.releaseFuture(controllerFuture)
    }

}