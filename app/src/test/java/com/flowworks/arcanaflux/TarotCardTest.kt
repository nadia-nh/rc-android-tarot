package com.flowworks.arcanaflux

import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.domain.model.Rank
import com.flowworks.arcanaflux.domain.model.Suit
import com.flowworks.arcanaflux.domain.model.TarotCard
import com.flowworks.arcanaflux.domain.model.getImageFileName
import com.flowworks.arcanaflux.domain.model.getMeaning
import com.flowworks.arcanaflux.domain.model.withRankAndSuit
import org.junit.Assert.*
import org.junit.Test

class TarotCardTest {

    @Test
    fun rankFromLabel_parsesAce() {
        assertEquals(Rank.Ace, Rank.fromLabel("Ace"))
    }

    @Test
    fun rankFromLabel_caseInsensitive() {
        assertEquals(Rank.Ace, Rank.fromLabel("ace"))
        assertEquals(Rank.Ace, Rank.fromLabel("ACE"))
    }

    @Test
    fun rankFromLabel_parsesKing() {
        assertEquals(Rank.King, Rank.fromLabel("KING"))
    }

    @Test
    fun rankFromLabel_returnsNullForInvalid() {
        assertNull(Rank.fromLabel("Invalid"))
    }

    @Test
    fun rankFromLabel_parsesAllRanks() {
        assertEquals(Rank.Two, Rank.fromLabel("Two"))
        assertEquals(Rank.Three, Rank.fromLabel("Three"))
        assertEquals(Rank.Four, Rank.fromLabel("Four"))
        assertEquals(Rank.Five, Rank.fromLabel("Five"))
        assertEquals(Rank.Six, Rank.fromLabel("Six"))
        assertEquals(Rank.Seven, Rank.fromLabel("Seven"))
        assertEquals(Rank.Eight, Rank.fromLabel("Eight"))
        assertEquals(Rank.Nine, Rank.fromLabel("Nine"))
        assertEquals(Rank.Ten, Rank.fromLabel("Ten"))
        assertEquals(Rank.Page, Rank.fromLabel("Page"))
        assertEquals(Rank.Knight, Rank.fromLabel("Knight"))
        assertEquals(Rank.Queen, Rank.fromLabel("Queen"))
    }

    @Test
    fun getImageFileName_majorArcana() {
        val card = TarotCard(
            name = "The Fool",
            uprightMeaning = "New beginnings",
            reversedMeaning = "Recklessness",
            suit = Suit.MajorArcana
        )
        assertEquals("major-the_fool.png", card.getImageFileName())
    }

    @Test
    fun getImageFileName_minorArcana() {
        val card = TarotCard(
            name = "Ace of Wands",
            uprightMeaning = "Inspiration",
            reversedMeaning = "Delays",
            suit = Suit.Wands,
            rank = Rank.Ace
        )
        assertEquals("wands-ace.png", card.getImageFileName())
    }

    @Test
    fun getImageFileName_pentacles() {
        val card = TarotCard(
            name = "King of Pentacles",
            uprightMeaning = "Wealth",
            reversedMeaning = "Greed",
            suit = Suit.Pentacles,
            rank = Rank.King
        )
        assertEquals("pentacles-king.png", card.getImageFileName())
    }

    @Test
    fun getMeaning_upright() {
        val card = TarotCard(
            name = "The Fool",
            uprightMeaning = "New beginnings",
            reversedMeaning = "Recklessness"
        )
        val drawnCard = DrawnCard(card = card, isReversed = false)
        assertEquals("New beginnings", drawnCard.getMeaning())
    }

    @Test
    fun getMeaning_reversed() {
        val card = TarotCard(
            name = "The Fool",
            uprightMeaning = "New beginnings",
            reversedMeaning = "Recklessness"
        )
        val drawnCard = DrawnCard(card = card, isReversed = true)
        assertEquals("Recklessness", drawnCard.getMeaning())
    }

    @Test
    fun withRankAndSuit_detectsWands() {
        val card = TarotCard(
            name = "Ace of Wands",
            uprightMeaning = "Inspiration",
            reversedMeaning = "Delays"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals(Suit.Wands, processedCard.suit)
        assertEquals(Rank.Ace, processedCard.rank)
    }

    @Test
    fun withRankAndSuit_detectsCups() {
        val card = TarotCard(
            name = "Queen of Cups",
            uprightMeaning = "Compassion",
            reversedMeaning = "Codependency"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals(Suit.Cups, processedCard.suit)
        assertEquals(Rank.Queen, processedCard.rank)
    }

    @Test
    fun withRankAndSuit_detectsSwords() {
        val card = TarotCard(
            name = "Ten of Swords",
            uprightMeaning = "Endings",
            reversedMeaning = "Recovery"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals(Suit.Swords, processedCard.suit)
        assertEquals(Rank.Ten, processedCard.rank)
    }

    @Test
    fun withRankAndSuit_detectsPentacles() {
        val card = TarotCard(
            name = "Page of Pentacles",
            uprightMeaning = "Ambition",
            reversedMeaning = "Laziness"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals(Suit.Pentacles, processedCard.suit)
        assertEquals(Rank.Page, processedCard.rank)
    }

    @Test
    fun withRankAndSuit_detectsMajorArcana() {
        val card = TarotCard(
            name = "The Fool",
            uprightMeaning = "New beginnings",
            reversedMeaning = "Recklessness"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals(Suit.MajorArcana, processedCard.suit)
        assertNull(processedCard.rank)
    }

    @Test
    fun withRankAndSuit_preservesOtherFields() {
        val card = TarotCard(
            name = "Knight of Wands",
            uprightMeaning = "Energy",
            reversedMeaning = "Recklessness"
        )
        val processedCard = card.withRankAndSuit()
        assertEquals("Knight of Wands", processedCard.name)
        assertEquals("Energy", processedCard.uprightMeaning)
        assertEquals("Recklessness", processedCard.reversedMeaning)
    }
}
