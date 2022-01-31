package com.skarlat.tripexpenses.ui.model

data class ExpenseInfo(
    val amount: Int,
    val debt: Int,
    val debtors: List<Debtor>,
    val description: String
)

val emptyExpenseInfo get() = ExpenseInfo(0, 0, listOf(), "")
