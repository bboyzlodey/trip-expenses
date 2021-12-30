package com.skarlat.tripexpenses.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseDebtor(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "expense_id") val expenseId: String,
    @ColumnInfo(name = "debt_amount") val debtAmount: Int,
    @ColumnInfo(name = "participant_id") val participantId: String,
    @ColumnInfo(name = "is_debt_payed") val isDebtPayed: Boolean,
)
