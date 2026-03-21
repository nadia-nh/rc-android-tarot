package com.example.simpletarot.data.remote

import com.example.simpletarot.domain.model.TarotCard
import com.example.simpletarot.domain.model.withRankAndSuit

fun ApiCard.toDomainModel(): TarotCard {
    return TarotCard(
        name = this.name,
        uprightMeaning = this.meaning_up,
        reversedMeaning = this.meaning_rev)
        .withRankAndSuit()
}
