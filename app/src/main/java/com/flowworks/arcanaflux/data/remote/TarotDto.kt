package com.flowworks.arcanaflux.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class TarotApiResponse(
    val count: Int,
    val cards: List<ApiCard>
)

@Serializable
data class ApiCard(
    val id: String,
    val name: String,
    val suit: String,
    val rank: String? = null,
    val value: Int,
    val upright: ApiCardMeaning,
    val reversed: ApiCardMeaning,
)

@Serializable
data class ApiCardMeaning(
    val keywords: List<String> = emptyList(),
    val meaning: String = "",
    val reflection: String = "",
)
