package com.example.simpletarot.database

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
