package com.dennydev.spotyrex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//@Dao
//interface SongDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addToPlaylist(song: List<Songs>)
//
//    @Query("SELECT * FROM playlist")
//    fun getAllSong(): Flow<List<Songs>>
//
//    @Query("DELETE FROM playlist")
//    suspend fun deleteAllSong()
//}