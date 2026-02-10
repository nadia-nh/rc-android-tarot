package com.example.simpletarot.database

import DrawnCardEntity
import ReadingEntity
import TarotDao

class TarotRepository(private val tarotDao: TarotDao) {
    suspend fun saveReading(reading: ReadingEntity, cards: List<DrawnCardEntity>) {
        val id = tarotDao.insertReading(reading)
        val cardsWithOwner = cards.map { it.copy(readingOwnerId = id) }
        tarotDao.insertDrawnCards(cardsWithOwner)
    }
}
