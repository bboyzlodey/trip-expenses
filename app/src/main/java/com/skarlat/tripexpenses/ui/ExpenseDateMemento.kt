package com.skarlat.tripexpenses.ui

import com.google.android.material.datepicker.MaterialDatePicker
import com.skarlat.tripexpenses.utils.DateFormatter
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@ActivityRetainedScoped
class ExpenseDateMemento @Inject constructor(private val dateFormatter: DateFormatter) {

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