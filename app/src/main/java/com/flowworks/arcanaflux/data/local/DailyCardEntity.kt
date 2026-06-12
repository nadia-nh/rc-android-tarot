package com.flowworks.arcanaflux.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.flowworks.arcanaflux.util.DateUtils

@Entity(
    tableName = "daily_readings",
    indices = [Index(value = ["date"], unique = true)]
)
data class DailyCardEntity(
    @PrimaryKey(autoGenerate = true) val dailyId: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val date: String = DateUtils.formatForDb(timestamp),
    @Embedded val card: DrawnCardEntity
)
