package com.example.simpletarot.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.simpletarot.data.DrawnCard
import com.example.simpletarot.data.Rank
import com.example.simpletarot.data.Suit
import com.example.simpletarot.data.TarotCard

@Entity(
    tableName = "drawn_cards",
    foreignKeys = [
        ForeignKey(
            entity = ReadingEntity::class,
            parentColumns = ["readingId"],
            childColumns = ["readingOwnerId"],
            onDelete = ForeignKey.CASCADE // If reading is deleted, delete these cards too
        )
    ]
)
data class DrawnCardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    val readingOwnerId: Long,
    val name: String,
    val isReversed: Boolean,
    val suit: String,
    val rank: String?
)

fun DrawnCard.toEntity(readingId: Long): DrawnCardEntity = DrawnCardEntity(
    readingOwnerId = readingId,
    name = card.name,
    isReversed = isReversed,
    suit = card.suit.name,
    rank = card.rank?.name,
)

fun DrawnCardEntity.toDrawnCard(): DrawnCard = DrawnCard(
    card = TarotCard(
        name = name,
        uprightMeaning = "",
        reversedMeaning = "",
        suit = Suit.valueOf(suit),
        rank = Rank.valueOf(rank ?: "MajorArcana")
    ),
    isReversed = isReversed
)
