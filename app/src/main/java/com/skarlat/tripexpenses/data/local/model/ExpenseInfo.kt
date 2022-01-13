package com.skarlat.tripexpenses.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.entity.Participant

data class ExpenseInfo(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "owner_id",
        entityColumn = "id",
        projection = ["name"],
        entity = Participant::class
    ) val ownerName: String
)
