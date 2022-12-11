package com.skarlat.tripexpenses.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey @ColumnInfo(name = "expense_id") val id: String,
    @ColumnInfo(name = "owner_id") val ownerId: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "trip_id") val tripId: String,
    @Deprecated("unused")
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: String,
)
