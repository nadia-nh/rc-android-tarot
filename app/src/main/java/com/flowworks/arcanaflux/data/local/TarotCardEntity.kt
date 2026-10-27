package com.flowworks.arcanaflux.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flowworks.arcanaflux.domain.model.Rank
import com.flowworks.arcanaflux.domain.model.Suit
import com.flowworks.arcanaflux.domain.model.TarotCard

@Entity(tableName = "tarot_cards")
data class TarotCardEntity(
    @PrimaryKey val name: String,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val uprightReflection: String,
    val reversedReflection: String,
    val uprightKeywords: String,
    val reversedKeywords: String,
    val suit: String,
    val rank: String?
)

fun TarotCard.toEntity(): TarotCardEntity = TarotCardEntity(
    name = name,
    uprightMeaning = uprightMeaning,
    reversedMeaning = reversedMeaning,
    uprightReflection = uprightReflection,
    reversedReflection = reversedReflection,
    uprightKeywords = uprightKeywords.joinToString(", "),
    reversedKeywords = reversedKeywords.joinToString(", "),
    suit = suit.name,
    rank = rank?.name,
)

fun TarotCardEntity.toTarotCard(): TarotCard = TarotCard(
    name = name,
    uprightMeaning = uprightMeaning,
    reversedMeaning = reversedMeaning,
    uprightReflection = uprightReflection,
    reversedReflection = reversedReflection,
    uprightKeywords = if (uprightKeywords.isBlank()) emptyList() else uprightKeywords.split(",").map { it.trim() }.filter { it.isNotEmpty() },
    reversedKeywords = if (reversedKeywords.isBlank()) emptyList() else reversedKeywords.split(",").map { it.trim() }.filter { it.isNotEmpty() },
    suit = try { Suit.valueOf(suit) } catch (_: Exception) { Suit.MajorArcana },
    rank = rank?.let { try { Rank.valueOf(it) } catch (_: Exception) { null } },
)
