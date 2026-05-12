package com.flowworks.arcanaflux.data.remote

import com.flowworks.arcanaflux.domain.model.TarotCard
import com.flowworks.arcanaflux.domain.model.withRankAndSuit

fun ApiCard.toDomainModel(): TarotCard {
    return TarotCard(
        name = convertName(this.name),
        uprightMeaning = this.meaning_up,
        reversedMeaning = this.meaning_rev)
        .withRankAndSuit()
}

private fun convertName(name: String) =
    when (name) {
        "Fortitude" -> "Strength"
        "The Last Judgment" -> "Judgement"
        else -> name
    }

