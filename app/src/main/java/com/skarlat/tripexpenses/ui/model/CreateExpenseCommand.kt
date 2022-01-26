package com.skarlat.tripexpenses.ui.model

data class CreateExpenseCommand(
    val date: String,
    val totalAmount: Int,
    val distributions: List<Distribution>,
    val payOwnerId: String,
    val tripId: String
)
