package com.skarlat.tripexpenses.utils

sealed interface DialogData {

    class DatePicker(
        val onDateSelected: (dateInMillis: Long) -> Unit,
        val negativeButtonClicked: () -> Unit,
    ) : DialogData

}