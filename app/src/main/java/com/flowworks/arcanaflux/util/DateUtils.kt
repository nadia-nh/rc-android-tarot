package com.flowworks.arcanaflux.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun formatTimestampDate(
        timestamp: Long,
        pattern: String = TIMESTAMP_FORMAT_DATE_DISPLAY) : String {
        val sdf = SimpleDateFormat(
            pattern,
            Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun getTodayDate(): String {
        return formatTimestampDate(
            System.currentTimeMillis(),
            TIMESTAMP_FORMAT_DATE_STORAGE)
    }

    private const val TIMESTAMP_FORMAT_DATE_DISPLAY = "MMM dd, yyyy"
    private const val TIMESTAMP_FORMAT_DATE_STORAGE = "yyyy-MM-dd"
}
