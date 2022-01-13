package com.skarlat.tripexpenses.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.entity.Participant

data class DebtorInfo(
    @Embedded val debtor: ExpenseDebtor,
    @Relation(
        parentColumn = "participant_id",
        entityColumn = "id"
    )
    val participant: Participant
)
