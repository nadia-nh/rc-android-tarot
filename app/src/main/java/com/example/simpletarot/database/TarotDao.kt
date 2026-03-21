package com.example.simpletarot.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.simpletarot.data.local.DrawnCardEntity
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.ReadingWithCards
import kotlinx.coroutines.flow.Flow

@Dao
interface TarotDao {
    @Insert
    suspend fun insertReading(reading: ReadingEntity): Long

    @Insert
    suspend fun insertDrawnCards(cards: List<DrawnCardEntity>)
    @Query("SELECT * FROM readings ORDER BY timestamp DESC")
    fun getAllReadingsWithCards(): Flow<List<ReadingWithCards>>

    @Delete
    suspend fun deleteReading(reading: ReadingEntity)
}
