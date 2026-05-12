package com.flowworks.arcanaflux.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceCards(cards: List<TarotCardEntity>)

    @Query("SELECT * FROM tarot_cards ORDER BY name")
    suspend fun getAllCards(): List<TarotCardEntity>

    @Query("SELECT * FROM tarot_cards WHERE name = :name LIMIT 1")
    suspend fun getCardByName(name: String): TarotCardEntity?

    @Query("SELECT COUNT(*) FROM tarot_cards")
    suspend fun getDeckCount(): Int
}