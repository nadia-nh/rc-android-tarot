package com.flowworks.arcanaflux.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class TarotApiResponse(
    val nhits: Int,
    val cards: List<ApiCard>
)

@Serializable
data class ApiCard(
    val name_short: String,
    val name: String,
    val meaning_up: String,
    val meaning_rev: String,
    val type: String, // Major or Minor
    val value: String,
    val value_int: Int,
    val desc: String, // Interpretation/Description
)
