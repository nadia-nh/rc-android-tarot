package com.flowworks.arcanaflux

import com.flowworks.arcanaflux.util.DateUtils
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.text.SimpleDateFormat

class DateUtilsTest {

    @Test
    fun formatTimestampDate_allMonths() {
        val months = listOf(
            Calendar.JANUARY,
            Calendar.FEBRUARY,
            Calendar.MARCH,
            Calendar.APRIL,
            Calendar.MAY,
            Calendar.JUNE,
            Calendar.JULY,
            Calendar.AUGUST,
            Calendar.SEPTEMBER,
            Calendar.OCTOBER,
            Calendar.NOVEMBER,
            Calendar.DECEMBER
        )

        for (monthIndex in months) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.set(2024, monthIndex, 15, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val timestamp = calendar.timeInMillis

            val result = DateUtils.formatTimestampDate(timestamp)
            val expectedFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
            val expected = expectedFormat.format(java.util.Date(timestamp))

            assertEquals(
                "Month $monthIndex: Expected '$expected' but got '$result'",
                expected,
                result
            )
        }
    }

    @Test
    fun formatTimestampDate_differentYears() {
        val years = listOf(2020, 2024, 2050)
        
        for (year in years) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.set(year, Calendar.JUNE, 15, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val timestamp = calendar.timeInMillis

            val result = DateUtils.formatTimestampDate(timestamp)

            assertTrue(
                "Expected year $year in result '$result'",
                result.endsWith(year.toString())
            )
        }
    }

    @Test
    fun formatTimestampDate_variousDays() {
        val days = listOf(1, 5, 15, 25)
        
        for (day in days) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.set(2024, Calendar.JUNE, day, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val timestamp = calendar.timeInMillis

            val result = DateUtils.formatTimestampDate(timestamp)
            val expectedFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
            val expected = expectedFormat.format(java.util.Date(timestamp))

            assertEquals(
                "Day $day: Expected '$expected' but got '$result'",
                expected,
                result
            )
        }
    }
}
