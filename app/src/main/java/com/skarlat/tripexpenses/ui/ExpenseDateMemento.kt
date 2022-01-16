package com.skarlat.tripexpenses.ui

import com.google.android.material.datepicker.MaterialDatePicker
import com.skarlat.tripexpenses.utils.DateFormatter
import kotlinx.coroutines.flow.*
import java.time.format.DateTimeFormatter

class ExpenseDateMemento(private val dateFormatter: DateFormatter) {

    private val timeStamp = MutableStateFlow(MaterialDatePicker.thisMonthInUtcMilliseconds())

    private val dateISO = timeStamp.map {
        dateFormatter.formatFromInstant(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    val readableDateFlow = timeStamp.map {
        dateFormatter.formatFromInstant(it, DateFormatter.DD_MMM)
    }

    suspend fun getSelectedDateISO() : String = dateISO.first()

    suspend fun selectTimestamp(time: Long) {
        timeStamp.emit(time)
    }
}