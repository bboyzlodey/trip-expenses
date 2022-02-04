package com.skarlat.tripexpenses.data.local.model

import androidx.room.ColumnInfo

data class ExpenseInfoItem(
    @ColumnInfo(name = "expense_id")
    val expenseId: String,
    @ColumnInfo(name = "is_payed")
    val isPayed: Boolean,
    @ColumnInfo(name = "amount")
    val totalAmount: Int,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "description")
    val description: String,
)