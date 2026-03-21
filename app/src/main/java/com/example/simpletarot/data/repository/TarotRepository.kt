package com.example.simpletarot.data.repository

import com.example.simpletarot.data.local.DrawnCardEntity
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.ReadingWithCards
import com.example.simpletarot.data.local.TarotDao
import kotlinx.coroutines.flow.Flow

class TarotRepository(private val tarotDao: TarotDao) {
    val allReadings: Flow<List<ReadingWithCards>> = tarotDao
        .getAllReadingsWithCards()

    suspend fun saveReading(reading: ReadingEntity, cards: List<DrawnCardEntity>) {
        val id = tarotDao.insertReading(reading)
        val cardsWithOwner = cards.map { it.copy(readingOwnerId = id) }
        tarotDao.insertDrawnCards(cardsWithOwner)
    }

    suspend fun deleteReading(reading: ReadingEntity) {
        tarotDao.deleteReading(reading)
    }
}