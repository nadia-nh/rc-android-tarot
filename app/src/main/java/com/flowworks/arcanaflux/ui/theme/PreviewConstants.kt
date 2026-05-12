package com.flowworks.arcanaflux.ui.theme

import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.domain.model.TarotCard
import com.flowworks.arcanaflux.domain.model.withRankAndSuit
import com.flowworks.arcanaflux.data.local.ReadingEntity
import com.flowworks.arcanaflux.data.local.ReadingWithCards
import com.flowworks.arcanaflux.data.local.toEntity

object PreviewConstants {
    val tarotCard = TarotCard(
        "The Fool",
        "New beginnings, spontaneity, trust",
        "Recklessness, naivety, holding back"
    )
        .withRankAndSuit()
    val drawnCard = DrawnCard(tarotCard, false, isRevealed = true)

    val tarotCards = listOf(
        TarotCard(
            "Knight of Pentacles",
            "Reliability, hard work, responsibility",
            "Stagnation, boredom, laziness"
        ),
        TarotCard(
            "Nine of Swords",
            "Anxiety, guilt, worry",
            "Hope, comfort, letting go of fear"
        ),
        TarotCard(
            "Two of Cups",
            "Connection, partnership, attraction",
            "Breakup, imbalance, tension"
        ),
    ).map { card ->
        DrawnCard(
            card = card.withRankAndSuit(),
            isReversed = false,
            isRevealed = true
        )
    }

     val readingEntities = listOf(
         ReadingEntity(readingId = 1, spreadType = "ThreeCardDraw"),
         ReadingEntity(readingId = 2, spreadType = "SingleCardDraw"),
         ReadingEntity(readingId = 3, spreadType = "ThreeCardDraw"),
     )

    val cardEntities = tarotCards.mapIndexed { index, card ->
        card.toEntity((index + 1).toLong())
    }

    val readings = readingEntities.map { reading ->
        ReadingWithCards(
            reading = reading,
            cards = cardEntities.filter { it.readingOwnerId == reading.readingId }
        )
    }

    val readingWithCards = ReadingWithCards(
        reading = readingEntities[0],
        cards = cardEntities
    )
}
