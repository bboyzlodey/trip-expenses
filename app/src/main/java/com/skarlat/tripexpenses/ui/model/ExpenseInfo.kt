package com.skarlat.tripexpenses.ui.model

data class ExpenseInfo(
    val amount: Int,
    val debt: Int,
    val isPayed: Boolean,
    val debtors: List<Debtor>
)
