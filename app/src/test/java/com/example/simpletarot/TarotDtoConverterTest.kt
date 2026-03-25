package com.example.simpletarot

import com.example.simpletarot.data.remote.ApiCard
import com.example.simpletarot.data.remote.toDomainModel
import com.example.simpletarot.domain.model.Suit
import org.junit.Assert.*
import org.junit.Test

class TarotDtoConverterTest {

    @Test
    fun fortitudeConvertsToStrength() {
        val apiCard = ApiCard(
            name_short = "fortitude",
            name = "Fortitude",
            meaning_up = "Courage, patience",
            meaning_rev = "Self-doubt",
            type = "major",
            value = "viii",
            value_int = 8,
            desc = "Inner strength"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Strength", domainCard.name)
    }

    @Test
    fun lastJudgmentConvertsToJudgement() {
        val apiCard = ApiCard(
            name_short = "judgement",
            name = "The Last Judgment",
            meaning_up = "Awakening, reflection",
            meaning_rev = "Self-doubt",
            type = "major",
            value = "xx",
            value_int = 20,
            desc = "Judgement day"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Judgement", domainCard.name)
    }

    @Test
    fun otherNamesUnchanged() {
        val apiCard = ApiCard(
            name_short = "the_fool",
            name = "The Fool",
            meaning_up = "New beginnings",
            meaning_rev = "Recklessness",
            type = "major",
            value = "0",
            value_int = 0,
            desc = "A fool"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("The Fool", domainCard.name)
    }

    @Test
    fun toDomainModel_preservesUprightMeaning() {
        val apiCard = ApiCard(
            name_short = "the_magician",
            name = "The Magician",
            meaning_up = "Manifestation, power",
            meaning_rev = "Manipulation",
            type = "major",
            value = "i",
            value_int = 1,
            desc = "Skill"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Manifestation, power", domainCard.uprightMeaning)
    }

    @Test
    fun toDomainModel_preservesReversedMeaning() {
        val apiCard = ApiCard(
            name_short = "the_magician",
            name = "The Magician",
            meaning_up = "Manifestation, power",
            meaning_rev = "Manipulation",
            type = "major",
            value = "i",
            value_int = 1,
            desc = "Skill"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Manipulation", domainCard.reversedMeaning)
    }

    @Test
    fun minorArcanaCard_setsCorrectSuit() {
        val apiCard = ApiCard(
            name_short = "wands_ace",
            name = "Ace of Wands",
            meaning_up = "Inspiration",
            meaning_rev = "Delays",
            type = "minor",
            value = "ace",
            value_int = 1,
            desc = "New opportunity"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals(Suit.Wands, domainCard.suit)
    }

    @Test
    fun minorArcanaCard_setsRank() {
        val apiCard = ApiCard(
            name_short = "cups_queen",
            name = "Queen of Cups",
            meaning_up = "Compassion",
            meaning_rev = "Codependency",
            type = "minor",
            value = "queen",
            value_int = 12,
            desc = "Emotional wisdom"
        )
        val domainCard = apiCard.toDomainModel()
        assertNotNull(domainCard.rank)
    }

    @Test
    fun majorArcanaCard_setsMajorArcanaSuit() {
        val apiCard = ApiCard(
            name_short = "the_empress",
            name = "The Empress",
            meaning_up = "Fertility, nurturing",
            meaning_rev = "Creative block",
            type = "major",
            value = "iii",
            value_int = 3,
            desc = "Mother"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals(Suit.MajorArcana, domainCard.suit)
    }

    @Test
    fun majorArcanaCard_hasNullRank() {
        val apiCard = ApiCard(
            name_short = "the_empress",
            name = "The Empress",
            meaning_up = "Fertility, nurturing",
            meaning_rev = "Creative block",
            type = "major",
            value = "iii",
            value_int = 3,
            desc = "Mother"
        )
        val domainCard = apiCard.toDomainModel()
        assertNull(domainCard.rank)
    }

    @Test
    fun allCupsSuits_converted() {
        val cupRanks = listOf("ace", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "page", "knight", "queen", "king")
        
        for (rank in cupRanks) {
            val apiCard = ApiCard(
                name_short = "cups_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Cups",
                meaning_up = "Meaning up",
                meaning_rev = "Meaning rev",
                type = "minor",
                value = rank,
                value_int = 1,
                desc = "Description"
            )
            val domainCard = apiCard.toDomainModel()
            assertEquals("cups_$rank should have Cups suit", Suit.Cups, domainCard.suit)
        }
    }

    @Test
    fun allSwordsSuits_converted() {
        val swordRanks = listOf("ace", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "page", "knight", "queen", "king")
        
        for (rank in swordRanks) {
            val apiCard = ApiCard(
                name_short = "swords_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Swords",
                meaning_up = "Meaning up",
                meaning_rev = "Meaning rev",
                type = "minor",
                value = rank,
                value_int = 1,
                desc = "Description"
            )
            val domainCard = apiCard.toDomainModel()
            assertEquals("swords_$rank should have Swords suit", Suit.Swords, domainCard.suit)
        }
    }

    @Test
    fun allPentaclesSuits_converted() {
        val pentacleRanks = listOf("ace", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "page", "knight", "queen", "king")
        
        for (rank in pentacleRanks) {
            val apiCard = ApiCard(
                name_short = "pentacles_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Pentacles",
                meaning_up = "Meaning up",
                meaning_rev = "Meaning rev",
                type = "minor",
                value = rank,
                value_int = 1,
                desc = "Description"
            )
            val domainCard = apiCard.toDomainModel()
            assertEquals("pentacles_$rank should have Pentacles suit", Suit.Pentacles, domainCard.suit)
        }
    }
}
