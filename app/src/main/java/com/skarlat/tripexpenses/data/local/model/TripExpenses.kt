package com.skarlat.tripexpenses.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.entity.Trip

data class TripExpenses(
    @Embedded val trip: Trip,
    @Relation(parentColumn = "id", entityColumn = "trip_id")
    val expenses: List<Expense>
)
