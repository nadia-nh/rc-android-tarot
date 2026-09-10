package com.flowworks.arcanaflux.data.remote

import com.flowworks.arcanaflux.domain.model.TarotCard
import com.flowworks.arcanaflux.domain.model.withRankAndSuit

fun ApiCard.toDomainModel(): TarotCard {
    return TarotCard(
        name = convertName(this.name),
        uprightMeaning = this.upright.meaning,
        reversedMeaning = this.reversed.meaning,
        uprightKeywords = this.upright.keywords,
        reversedKeywords = this.reversed.keywords,
        uprightReflection = this.upright.reflection,
        reversedReflection = this.reversed.reflection)
        .withRankAndSuit()
}

private fun convertName(name: String) =
    when (name) {
        "Fortitude" -> "Strength"
        "The Last Judgment" -> "Judgement"
        "Wheel of Fortune" -> "Wheel Of Fortune"
        else -> name
    }

