package com.skarlat.tripexpenses.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatter @Inject constructor() {

    companion object Formats {
        const val DD_MMM = "DD MMM"
        const val DD_MM_YY = "DD.MM.YY"
    }

    fun formatFromInstant(millis: Long, format: String): String {
        return format(format, LocalDateTime.ofInstant(millis.toInstant(), ZoneId.systemDefault()))
    }

    fun formatFromInstant(millis: Long, formatter: DateTimeFormatter): String {
        return formatter.format(LocalDateTime.ofInstant(millis.toInstant(), ZoneId.systemDefault()))
    }

    private fun Long.toInstant(): Instant = Instant.ofEpochMilli(this)

    private fun format(pattern: String, temporalAccessor: TemporalAccessor): String {
        return DateTimeFormatter.ofPattern(pattern).format(temporalAccessor)
    }
}