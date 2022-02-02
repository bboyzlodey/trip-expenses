package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.utils.DateFormatter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatterTest {

    private val dateFormatter = DateFormatter()

    @Before
    fun setup() {
        Locale.setDefault(Locale("RU"))
    }

    @Test
    fun localDateTest_DD_MMM() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_YEAR, 15)
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
        val factResult = dateFormatter.formatFromInstant(calendar.timeInMillis, DateFormatter.DD_MMM)
        val actualResult = "15 янв."
        assertEquals(factResult, actualResult)
    }

    @Test
    fun localDate_ISO() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_YEAR, 15)
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
        val factResult =
            dateFormatter.formatFromInstant(calendar.timeInMillis, DateTimeFormatter.ISO_LOCAL_DATE)
        val actualResult = "2022-01-15"
        assertEquals(factResult, actualResult)
    }

    @Test
    fun now() {
        val actualResult = DateTimeFormatter.ofPattern("dd MMM").format(LocalDate.now())
        val factResult =
            dateFormatter.formatFromInstant(Instant.now().toEpochMilli(), DateFormatter.DD_MMM)
        assertEquals(factResult, actualResult)
    }
}