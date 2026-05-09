package com.example.simpletarot.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

interface TarotApiService {
    @GET("api/v1/cards/")
    suspend fun getAllCards(): TarotApiResponse

    companion object {
        private const val BASE_URL = "https://tarotapi.dev/"
        private val json = Json { ignoreUnknownKeys = true }

        fun create(): TarotApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    json
                        .asConverterFactory(
                            "application/json".toMediaType()
                        ))
                .build()
                .create(TarotApiService::class.java)
        }
    }
}
