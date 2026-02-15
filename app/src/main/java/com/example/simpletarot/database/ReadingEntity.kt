package com.example.simpletarot.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Relation


@Entity(tableName = "readings")
data class ReadingEntity(
    @PrimaryKey(autoGenerate = true) val readingId: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val spreadType: String
)

data class ReadingWithCards(
    @Embedded val reading: ReadingEntity,

    @Relation(
        parentColumn = "readingId",
        entityColumn = "readingOwnerId"
    )
    val cards: List<DrawnCardEntity>
)
