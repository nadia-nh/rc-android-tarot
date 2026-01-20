package com.example.simpletarot.data

data class TarotCard(
    val name: String,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val suit: Suit = Suit.MajorArcana,
    val rank: Rank? = null
)

enum class Suit(val displayName: String) {
    MajorArcana("Major Arcana"),
    Wands("Wands"),
    Cups("Cups"),
    Swords("Swords"),
    Pentacles("Pentacles")
}

enum class Rank {
    Ace,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Page,
    Knight,
    Queen,
    King;

    companion object {
        fun fromLabel(label: String): Rank? =
            entries.firstOrNull { it.name.equals(label, ignoreCase = true) }
    }
}

data class DrawnCard(
    val card: TarotCard,
    val isReversed: Boolean
)

private val minorArcanaSuits = Suit.entries.filter { it != Suit.MajorArcana }

fun TarotCard.withClassification(): TarotCard {
    val matchingSuit = minorArcanaSuits.firstOrNull { suit ->
        name.endsWith(" of ${suit.displayName}")
    } ?: return copy(suit = Suit.MajorArcana, rank = null)

    val rankName = name.removeSuffix(" of ${matchingSuit.displayName}").trim()
    val rank = Rank.fromLabel(rankName)
    return copy(suit = matchingSuit, rank = rank)
}
