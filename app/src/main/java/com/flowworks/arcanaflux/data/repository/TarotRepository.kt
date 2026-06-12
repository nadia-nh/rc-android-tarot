package com.flowworks.arcanaflux.data.repository

import android.util.Log
import com.flowworks.arcanaflux.data.local.DailyCardEntity
import com.flowworks.arcanaflux.data.local.DrawnCardEntity
import com.flowworks.arcanaflux.data.local.ReadingEntity
import com.flowworks.arcanaflux.data.local.ReadingWithCards
import com.flowworks.arcanaflux.data.local.TarotDao
import com.flowworks.arcanaflux.data.local.TarotDeck
import com.flowworks.arcanaflux.data.local.toEntity
import com.flowworks.arcanaflux.data.local.toTarotCard
import com.flowworks.arcanaflux.data.remote.TarotApiService
import com.flowworks.arcanaflux.data.remote.toDomainModel
import com.flowworks.arcanaflux.domain.model.TarotCard
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class TarotRepository(
    private val tarotApiService: TarotApiService,
    private val tarotDao: TarotDao) {
    val allReadings: Flow<List<ReadingWithCards>> = tarotDao
        .getAllReadingsWithCards()

    private val localDeck = TarotDeck.getDeck()

    private var cachedDeck: List<TarotCard>? = null

    suspend fun getFullDeck(
        useNetwork: Boolean): List<TarotCard> {
        val dbDeck = getDeckFromDb()
        if (dbDeck.isNotEmpty()) {
            cachedDeck = dbDeck
            return dbDeck
        }

        if (!useNetwork) {
            cachedDeck = localDeck
            return localDeck
        }

        try {
            val response = tarotApiService.getAllCards()
            val apiDeck = response.cards.map { apiCard ->
                apiCard.toDomainModel()
            }
            saveDeckToDb(apiDeck)
            cachedDeck = apiDeck
            return apiDeck
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("TarotAPI", "HTTP Error ${e.code()}: $errorBody")
        } catch (e: IOException) {
            Log.e("TarotAPI", "Network Error: ${e.message}")
        } catch (e: Exception) {
            Log.e("TarotAPI", "Unknown Error: ${e.message}")
        }

        cachedDeck = localDeck
        return localDeck
    }

    private suspend fun getDeckFromDb(): List<TarotCard> {
        return try {
            tarotDao.getAllCards().map { it.toTarotCard() }
        } catch (e: Exception) {
            Log.e("TarotRepository", "Error loading deck from DB: ${e.message}")
            emptyList()
        }
    }

    private suspend fun saveDeckToDb(deck: List<TarotCard>) {
        try {
            val entities = deck.map { it.toEntity() }
            tarotDao.insertOrReplaceCards(entities)
        } catch (e: Exception) {
            Log.e("TarotRepository", "Error saving deck to DB: ${e.message}")
        }
    }

    suspend fun saveReading(
        reading: ReadingEntity,
        cards: List<DrawnCardEntity>) {
        val id = tarotDao.insertReading(reading)
        val cardsWithOwner = cards.map { it.copy(readingOwnerId = id) }
        tarotDao.insertDrawnCards(cardsWithOwner)
    }

    suspend fun saveDailyReading(reading: DailyCardEntity) {
        tarotDao.insertDailyReading(reading)
    }

    suspend fun getDailyReadingByDate(date: String): DailyCardEntity? {
        return tarotDao.getDailyReadingByDate(date)
    }

    suspend fun deleteReading(reading: ReadingEntity) {
        tarotDao.deleteReading(reading)
    }
}