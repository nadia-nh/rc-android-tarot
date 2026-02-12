package com.example.simpletarot.data

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
}
