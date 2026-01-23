package com.example.simpletarot.data

import com.example.simpletarot.R

data class TarotCard(
    val name: String,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val suit: Suit = Suit.MajorArcana,
    val rank: Rank? = null,
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
    val isReversed: Boolean,
    var isRevealed: Boolean,
)

private val minorArcanaSuits = Suit.entries.filter { it != Suit.MajorArcana }

private fun TarotCard.getSuit() : Suit {
    return minorArcanaSuits.firstOrNull {
        val suitName = it.displayName
        name.endsWith(" of $suitName")
    } ?: Suit.MajorArcana
}

private fun getRankFromName(name: String, suit: Suit): Rank? {
    if (suit == Suit.MajorArcana) {
        return null
    }

    val suitName = suit.displayName
    val rankName = name.removeSuffix(" of $suitName").trim()
    return Rank.fromLabel(rankName)
}

fun TarotCard.withRankAndSuit(): TarotCard {
    val suit = getSuit()
    return copy(
        suit = suit,
        rank = getRankFromName(name, suit))
}

fun TarotCard.getImageFileName(): String {
    return if (suit == Suit.MajorArcana) {
        val formattedName = name.lowercase().replace(" ", "_")
        "major-$formattedName.png"
    } else {
        val suitName = suit.displayName.lowercase()
        val rankName = rank?.name?.lowercase() ?: ""
        "${suitName}-$rankName.png"
    }
}

fun TarotCard.getResourceId(): Int {
    return if (suit == Suit.MajorArcana) {
        when (name) {
            "The Fool" -> R.drawable.major_the_fool
            "The Magician" -> R.drawable.major_the_magician
            "The High Priestess" -> R.drawable.major_the_high_priestess
            "The Empress" -> R.drawable.major_the_empress
            "The Emperor" -> R.drawable.major_the_emperor
            "The Hierophant" -> R.drawable.major_the_hierophant
            "The Lovers" -> R.drawable.major_the_lovers
            "The Chariot" -> R.drawable.major_the_chariot
            "Strength" -> R.drawable.major_strength
            "The Hermit" -> R.drawable.major_the_hermit
            "Wheel of Fortune" -> R.drawable.major_wheel_of_fortune
            "Justice" -> R.drawable.major_justice
            "The Hanged Man" -> R.drawable.major_the_hanged_man
            "Death" -> R.drawable.major_death
            "Temperance" -> R.drawable.major_temperance
            "The Devil" -> R.drawable.major_the_devil
            "The Tower" -> R.drawable.major_the_tower
            "The Star" -> R.drawable.major_the_star
            "The Moon" -> R.drawable.major_the_moon
            "The Sun" -> R.drawable.major_the_sun
            "Judgement" -> R.drawable.major_judgement
            "The World" -> R.drawable.major_the_world

            else -> R.drawable.card_back
        }
    } else {
        when (suit) {
            Suit.Cups -> when (rank) {
                Rank.Ace -> R.drawable.cups_ace
                Rank.Two -> R.drawable.cups_two
                Rank.Three -> R.drawable.cups_three
                Rank.Four -> R.drawable.cups_four
                Rank.Five -> R.drawable.cups_five
                Rank.Six -> R.drawable.cups_six
                Rank.Seven -> R.drawable.cups_seven
                Rank.Eight -> R.drawable.cups_eight
                Rank.Nine -> R.drawable.cups_nine
                Rank.Ten -> R.drawable.cups_ten
                Rank.Page -> R.drawable.cups_page
                Rank.Knight -> R.drawable.cups_knight
                Rank.Queen -> R.drawable.cups_queen
                Rank.King -> R.drawable.cups_king
                else -> R.drawable.card_back
            }
            Suit.Swords -> when (rank) {
                Rank.Ace -> R.drawable.swords_ace
                Rank.Two -> R.drawable.swords_two
                Rank.Three -> R.drawable.swords_three
                Rank.Four -> R.drawable.swords_four
                Rank.Five -> R.drawable.swords_five
                Rank.Six -> R.drawable.swords_six
                Rank.Seven -> R.drawable.swords_seven
                Rank.Eight -> R.drawable.swords_eight
                Rank.Nine -> R.drawable.swords_nine
                Rank.Ten -> R.drawable.swords_ten
                Rank.Page -> R.drawable.swords_page
                Rank.Knight -> R.drawable.swords_knight
                Rank.Queen -> R.drawable.swords_queen
                Rank.King -> R.drawable.swords_king
                else -> R.drawable.card_back
            }
            Suit.Pentacles -> when (rank) {
                Rank.Ace -> R.drawable.pentacles_ace
                Rank.Two -> R.drawable.pentacles_two
                Rank.Three -> R.drawable.pentacles_three
                Rank.Four -> R.drawable.pentacles_four
                Rank.Five -> R.drawable.pentacles_five
                Rank.Six -> R.drawable.pentacles_six
                Rank.Seven -> R.drawable.pentacles_seven
                Rank.Eight -> R.drawable.pentacles_eight
                Rank.Nine -> R.drawable.pentacles_nine
                Rank.Ten -> R.drawable.pentacles_ten
                Rank.Page -> R.drawable.pentacles_page
                Rank.Knight -> R.drawable.pentacles_knight
                Rank.Queen -> R.drawable.pentacles_queen
                Rank.King -> R.drawable.pentacles_king
                else -> R.drawable.card_back
            }
            Suit.Wands -> when (rank) {
                Rank.Ace -> R.drawable.wands_ace
                Rank.Two -> R.drawable.wands_two
                Rank.Three -> R.drawable.wands_three
                Rank.Four -> R.drawable.wands_four
                Rank.Five -> R.drawable.wands_five
                Rank.Six -> R.drawable.wands_six
                Rank.Seven -> R.drawable.wands_seven
                Rank.Eight -> R.drawable.wands_eight
                Rank.Nine -> R.drawable.wands_nine
                Rank.Ten -> R.drawable.wands_ten
                Rank.Page -> R.drawable.wands_page
                Rank.Knight -> R.drawable.wands_knight
                Rank.Queen -> R.drawable.wands_queen
                Rank.King -> R.drawable.wands_king
                else -> R.drawable.card_back
            }
            else -> R.drawable.card_back
        }
    }
}