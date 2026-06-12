package com.flowworks.arcanaflux.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun formatTimestampDate(timestamp: Long): String {
        val format = TIMESTAMP_FORMAT_DATE
        val sdf = SimpleDateFormat(
            format,
            Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun getTodayDate(): String {
        return formatTimestampDate(
            System.currentTimeMillis())
    }

    private const val TIMESTAMP_FORMAT_DATE = "MMM dd, yyyy"
}
