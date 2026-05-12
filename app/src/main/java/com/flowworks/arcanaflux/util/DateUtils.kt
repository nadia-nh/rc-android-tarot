package com.flowworks.arcanaflux.util

object DateUtils {
    fun formatTimestampDate(timestamp: Long): String {
        val format = TIMESTAMP_FORMAT_DATE
        val sdf = java.text.SimpleDateFormat(
            format,
            java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }

    private const val TIMESTAMP_FORMAT_DATE = "MMM dd, yyyy"
}
