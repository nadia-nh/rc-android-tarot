package com.example.simpletarot.data

import com.example.simpletarot.database.DrawnCardEntity
import com.example.simpletarot.database.ReadingEntity
import com.example.simpletarot.database.ReadingWithCards
import com.example.simpletarot.database.toEntity

object PreviewConstants {
    val tarotCard = TarotCard("The Fool",
        "New beginnings, spontaneity, trust",
        "Recklessness, naivety, holding back")
        .withRankAndSuit()
    val drawnCard = DrawnCard(tarotCard, false, isRevealed = true)

    val tarotCards = listOf(
        TarotCard("Knight of Pentacles",
            "Reliability, hard work, responsibility",
            "Stagnation, boredom, laziness"),
        TarotCard("Nine of Swords",
            "Anxiety, guilt, worry",
            "Hope, comfort, letting go of fear"),
        TarotCard("Two of Cups",
            "Connection, partnership, attraction",
            "Breakup, imbalance, tension"),
    ).map { card ->
        DrawnCard(card = card.withRankAndSuit(),
            isReversed = false,
            isRevealed = true)
    }
     
     val readingEntities = listOf(
         ReadingEntity(spreadType = "ThreeCardDraw"),
         ReadingEntity(spreadType = "SingleCardDraw"),
         ReadingEntity(spreadType = "ThreeCardDraw"),
     )

    val cardEntities = tarotCards.mapIndexed { index, card ->
        card.toEntity(index.toLong())
    }

    val readings = readingEntities.mapIndexed { index, reading ->
        ReadingWithCards(
            reading = reading,
            cards = cardEntities.filter { it.readingOwnerId == index.toLong() }
        )
    }

    val readingWithCards = ReadingWithCards(
        reading = readingEntities[0],
        cards = cardEntities
    )
}
