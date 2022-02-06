package com.skarlat.tripexpenses.data.local.model

import androidx.room.ColumnInfo

data class DebtorPaidRequest(
    val id: String,
    @ColumnInfo(name = "is_debt_payed")
    val isDebtPayed: Boolean
)