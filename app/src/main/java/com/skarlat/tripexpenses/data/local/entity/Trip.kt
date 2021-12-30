package com.skarlat.tripexpenses.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trip(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "total_cost") val totalCost: Int,
    @ColumnInfo(name = "personal_cost") val personalCost: Int,
    @ColumnInfo(name = "debt") val debt: Int,
    @ColumnInfo(name = "name") val name: String,
)
