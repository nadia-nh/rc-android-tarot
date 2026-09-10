package com.flowworks.arcanaflux

import com.flowworks.arcanaflux.data.remote.ApiCard
import com.flowworks.arcanaflux.data.remote.ApiCardMeaning
import com.flowworks.arcanaflux.data.remote.toDomainModel
import com.flowworks.arcanaflux.domain.model.Suit
import org.junit.Assert.*
import org.junit.Test

class TarotDtoConverterTest {

    private fun createApiCard(
        id: String = "test_card",
        name: String,
        suit: String = "major_arcana",
        rank: String? = null,
        value: Int = 0,
        meaningUp: String = "Upright meaning",
        meaningRev: String = "Reversed meaning"
    ): ApiCard {
        return ApiCard(
            id = id,
            name = name,
            suit = suit,
            rank = rank,
            value = value,
            upright = ApiCardMeaning(
                keywords = listOf("keyword1", "keyword2"),
                meaning = meaningUp,
                reflection = "Reflection up"
            ),
            reversed = ApiCardMeaning(
                keywords = listOf("rev_keyword1"),
                meaning = meaningRev,
                reflection = "Reflection rev"
            )
        )
    }

    @Test
    fun fortitudeConvertsToStrength() {
        val apiCard = createApiCard(
            id = "fortitude",
            name = "Fortitude",
            meaningUp = "Courage, patience",
            meaningRev = "Self-doubt"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Strength", domainCard.name)
    }

    @Test
    fun lastJudgmentConvertsToJudgement() {
        val apiCard = createApiCard(
            id = "judgement",
            name = "The Last Judgment",
            meaningUp = "Awakening, reflection",
            meaningRev = "Self-doubt"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Judgement", domainCard.name)
    }

    @Test
    fun wheelOfFortuneConvertsToWheelOfFortune() {
        val apiCard = createApiCard(
            id = "wheel_of_fortune",
            name = "Wheel of Fortune",
            meaningUp = "Change, cycles",
            meaningRev = "Bad luck"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Wheel Of Fortune", domainCard.name)
    }

    @Test
    fun otherNamesUnchanged() {
        val apiCard = createApiCard(
            id = "the_fool",
            name = "The Fool",
            meaningUp = "New beginnings",
            meaningRev = "Recklessness"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("The Fool", domainCard.name)
    }

    @Test
    fun toDomainModel_preservesUprightMeaning() {
        val apiCard = createApiCard(
            id = "the_magician",
            name = "The Magician",
            meaningUp = "Manifestation, power",
            meaningRev = "Manipulation"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Manifestation, power", domainCard.uprightMeaning)
    }

    @Test
    fun toDomainModel_preservesReversedMeaning() {
        val apiCard = createApiCard(
            id = "the_magician",
            name = "The Magician",
            meaningUp = "Manifestation, power",
            meaningRev = "Manipulation"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals("Manipulation", domainCard.reversedMeaning)
    }

    @Test
    fun minorArcanaCard_setsCorrectSuit() {
        val apiCard = createApiCard(
            id = "wands_ace",
            name = "Ace of Wands",
            suit = "wands",
            rank = "ace",
            value = 1,
            meaningUp = "Inspiration",
            meaningRev = "Delays"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals(Suit.Wands, domainCard.suit)
    }

    @Test
    fun minorArcanaCard_setsRank() {
        val apiCard = createApiCard(
            id = "cups_queen",
            name = "Queen of Cups",
            suit = "cups",
            rank = "queen",
            value = 12,
            meaningUp = "Compassion",
            meaningRev = "Codependency"
        )
        val domainCard = apiCard.toDomainModel()
        assertNotNull(domainCard.rank)
    }

    @Test
    fun majorArcanaCard_setsMajorArcanaSuit() {
        val apiCard = createApiCard(
            id = "the_empress",
            name = "The Empress",
            suit = "major_arcana",
            rank = null,
            value = 3,
            meaningUp = "Fertility, nurturing",
            meaningRev = "Creative block"
        )
        val domainCard = apiCard.toDomainModel()
        assertEquals(Suit.MajorArcana, domainCard.suit)
    }

    @Test
    fun majorArcanaCard_hasNullRank() {
        val apiCard = createApiCard(
            id = "the_empress",
            name = "The Empress",
            suit = "major_arcana",
            rank = null,
            value = 3,
            meaningUp = "Fertility, nurturing",
            meaningRev = "Creative block"
        )
        val domainCard = apiCard.toDomainModel()
        assertNull(domainCard.rank)
    }

    @Test
    fun allCupsSuits_converted() {
        val cupRanks = listOf("ace", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "page", "knight", "queen", "king")

        for (rank in cupRanks) {
            val apiCard = createApiCard(
                id = "cups_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Cups",
                suit = "cups",
                rank = rank,
                value = 1,
                meaningUp = "Meaning up",
                meaningRev = "Meaning rev"
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
            val apiCard = createApiCard(
                id = "swords_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Swords",
                suit = "swords",
                rank = rank,
                value = 1,
                meaningUp = "Meaning up",
                meaningRev = "Meaning rev"
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
            val apiCard = createApiCard(
                id = "pentacles_$rank",
                name = "${rank.replaceFirstChar { it.uppercase() }} of Pentacles",
                suit = "pentacles",
                rank = rank,
                value = 1,
                meaningUp = "Meaning up",
                meaningRev = "Meaning rev"
            )
            val domainCard = apiCard.toDomainModel()
            assertEquals("pentacles_$rank should have Pentacles suit", Suit.Pentacles, domainCard.suit)
        }
    }
}
