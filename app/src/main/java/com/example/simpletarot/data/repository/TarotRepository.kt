package com.example.simpletarot.data.repository

import android.util.Log
import com.example.simpletarot.data.local.DrawnCardEntity
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.ReadingWithCards
import com.example.simpletarot.data.local.TarotDao
import com.example.simpletarot.data.local.TarotDeck
import com.example.simpletarot.data.remote.TarotApiService
import com.example.simpletarot.data.remote.toDomainModel
import com.example.simpletarot.domain.model.TarotCard
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class TarotRepository(
    private val tarotApiService: TarotApiService,
    private val tarotDao: TarotDao) {
    val allReadings: Flow<List<ReadingWithCards>> = tarotDao
        .getAllReadingsWithCards()

    private val localDeck = TarotDeck.getDeck()

    suspend fun getFullDeck(
        useNetwork: Boolean): List<TarotCard> {
        if (!useNetwork) return localDeck

        try {
            val response = tarotApiService.getAllCards()
            return response.cards.map { apiCard ->
                apiCard.toDomainModel()
            }
        } catch (e: HttpException) {
            // This triggers for 404, 500, etc.
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("TarotAPI", "HTTP Error ${e.code()}: $errorBody")
        } catch (e: IOException) {
            // This triggers for no internet or timeouts
            Log.e("TarotAPI", "Network Error: ${e.message}")
        } catch (e: Exception) {
            Log.e("TarotAPI", "Unknown Error: ${e.message}")
        }

        return localDeck
    }

    suspend fun saveReading(
        reading: ReadingEntity,
        cards: List<DrawnCardEntity>) {
        val id = tarotDao.insertReading(reading)
        val cardsWithOwner = cards.map { it.copy(readingOwnerId = id) }
        tarotDao.insertDrawnCards(cardsWithOwner)
    }

    suspend fun deleteReading(reading: ReadingEntity) {
        tarotDao.deleteReading(reading)
    }
}