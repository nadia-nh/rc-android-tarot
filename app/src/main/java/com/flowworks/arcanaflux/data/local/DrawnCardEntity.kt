package com.flowworks.arcanaflux.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.flowworks.arcanaflux.domain.model.DrawnCard
import com.flowworks.arcanaflux.domain.model.TarotCard

@Entity(
    tableName = "drawn_cards",
    foreignKeys = [
        ForeignKey(
            entity = ReadingEntity::class,
            parentColumns = ["readingId"],
            childColumns = ["readingOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DrawnCardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    val readingOwnerId: Long,
    val name: String,
    val isReversed: Boolean
)

fun DrawnCard.toEntity(readingId: Long): DrawnCardEntity = DrawnCardEntity(
    readingOwnerId = readingId,
    name = card.name,
    isReversed = isReversed
)

fun DrawnCardEntity.toDrawnCard(): DrawnCard {
    val lookedUpCard = TarotDeck.getCardByName(name)

    return DrawnCard(
        card = lookedUpCard ?: TarotCard(
            name = name,
            uprightMeaning = "",
            reversedMeaning = ""
        ),
        isReversed = isReversed
    )
}
