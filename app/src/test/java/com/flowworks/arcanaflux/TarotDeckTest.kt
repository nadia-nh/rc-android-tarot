package com.flowworks.arcanaflux

import com.flowworks.arcanaflux.data.local.TarotDeck
import com.flowworks.arcanaflux.domain.model.Suit
import org.junit.Assert.*
import org.junit.Test

class TarotDeckTest {

    @Test
    fun deckHas78Cards() {
        val deck = TarotDeck.getDeck()
        assertEquals("Deck should contain exactly 78 cards", 78, deck.size)
    }

    @Test
    fun deckHas22MajorArcana() {
        val deck = TarotDeck.getDeck()
        val majorArcana = deck.filter { it.suit == Suit.MajorArcana }
        assertEquals("Deck should contain exactly 22 Major Arcana cards", 22, majorArcana.size)
    }

    @Test
    fun deckHas56MinorArcana() {
        val deck = TarotDeck.getDeck()
        val minorArcana = deck.filter { it.suit != Suit.MajorArcana }
        assertEquals("Deck should contain exactly 56 Minor Arcana cards", 56, minorArcana.size)
    }



    @Test
    fun majorArcanaContainsTheFool() {
        val deck = TarotDeck.getDeck()
        val hasTheFool = deck.any { it.name == "The Fool" }
        assertTrue("Deck should contain 'The Fool'", hasTheFool)
    }

    @Test
    fun majorArcanaContainsTheWorld() {
        val deck = TarotDeck.getDeck()
        val hasTheWorld = deck.any { it.name == "The World" }
        assertTrue("Deck should contain 'The World'", hasTheWorld)
    }

    @Test
    fun majorArcanaContainsAll22Cards() {
        val deck = TarotDeck.getDeck()
        val majorArcanaNames = listOf(
            "The Fool",
            "The Magician",
            "The High Priestess",
            "The Empress",
            "The Emperor",
            "The Hierophant",
            "The Lovers",
            "The Chariot",
            "Strength",
            "The Hermit",
            "Wheel Of Fortune",
            "Justice",
            "The Hanged Man",
            "Death",
            "Temperance",
            "The Devil",
            "The Tower",
            "The Star",
            "The Moon",
            "The Sun",
            "Judgement",
            "The World"
        )
        val deckNames = deck.filter { it.suit == Suit.MajorArcana }.map { it.name }
        for (name in majorArcanaNames) {
            assertTrue("Deck should contain '$name'", deckNames.contains(name))
        }
    }

    @Test
    fun minorArcanaCardsHaveRanks() {
        val deck = TarotDeck.getDeck()
        val minorArcana = deck.filter { it.suit != Suit.MajorArcana }
        val cardsWithoutRank = minorArcana.filter { it.rank == null }
        assertTrue(
            "All Minor Arcana cards should have a rank. Missing ranks: ${cardsWithoutRank.map { it.name }}",
            cardsWithoutRank.isEmpty()
        )
    }

    @Test
    fun minorArcanaCardsHaveMeanings() {
        val deck = TarotDeck.getDeck()
        val minorArcana = deck.filter { it.suit != Suit.MajorArcana }
        for (card in minorArcana) {
            assertFalse("Card '${card.name}' should have upright meaning", card.uprightMeaning.isEmpty())
            assertFalse("Card '${card.name}' should have reversed meaning", card.reversedMeaning.isEmpty())
        }
    }

    @Test
    fun majorArcanaCardsHaveNoRank() {
        val deck = TarotDeck.getDeck()
        val majorArcana = deck.filter { it.suit == Suit.MajorArcana }
        val cardsWithRank = majorArcana.filter { it.rank != null }
        assertTrue(
            "Major Arcana cards should not have ranks. Found: ${cardsWithRank.map { "${it.name}: ${it.rank}" }}",
            cardsWithRank.isEmpty()
        )
    }

    @Test
    fun eachMinorSuitHasAllRanks() {
        val deck = TarotDeck.getDeck()
        val expectedRanks = listOf(
            "Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Page", "Knight", "Queen", "King"
        )

        for (suit in listOf(Suit.Wands, Suit.Cups, Suit.Swords, Suit.Pentacles)) {
            val suitCards = deck.filter { it.suit == suit }
            val rankNames = suitCards.mapNotNull { it.rank?.name }
            assertEquals(
                "Suit $suit should have 14 cards",
                14,
                suitCards.size
            )
            for (expectedRank in expectedRanks) {
                assertTrue(
                    "Suit $suit should contain '$expectedRank'",
                    rankNames.contains(expectedRank)
                )
            }
        }
    }
}
