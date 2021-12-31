package com.skarlat.tripexpenses.ui.model

data class Expense(
    val amount: String,
    val debt: String,
    val isPayed: Boolean,
    val id: String,
    val date: String
)
