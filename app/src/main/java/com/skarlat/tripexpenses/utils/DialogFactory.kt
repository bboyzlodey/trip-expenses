package com.skarlat.tripexpenses.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker

object DialogFactory {

    fun showDialog(fragmentManager: FragmentManager, dialogData: DialogData) {
        when (dialogData) {
            is DialogData.DatePicker -> showDialog(fragmentManager, dialogData)
        }
    }

    private fun showDialog(fragmentManager: FragmentManager, dialogData: DialogData.DatePicker) {
        MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()
            .apply {
                addOnPositiveButtonClickListener(dialogData.onDateSelected)
                addOnCancelListener { dialogData.negativeButtonClicked.invoke() }
                addOnDismissListener { dialogData.negativeButtonClicked.invoke() }
                addOnNegativeButtonClickListener { dialogData.negativeButtonClicked.invoke() }
            }
            .show(fragmentManager, null)
    }
}