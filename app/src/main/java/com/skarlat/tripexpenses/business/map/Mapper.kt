package com.skarlat.tripexpenses.business.map

import com.skarlat.tripexpenses.business.calculator.IDebtorCalculator
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.Const
import java.util.*
import com.skarlat.tripexpenses.data.local.entity.Expense as ExpenseEntity
import com.skarlat.tripexpenses.data.local.entity.Participant as EntityParticipant
import com.skarlat.tripexpenses.data.local.entity.Trip as TripEntity
import com.skarlat.tripexpenses.ui.model.Expense as ExpenseUIModel
import com.skarlat.tripexpenses.ui.model.Trip as TripUIModel

fun Iterable<TripEntity>.mapToUIModel(): List<TripUIModel> {
    return mapNotNull { trip: TripEntity ->
        TripUIModel(
            id = trip.id,
            name = trip.name
        )
    }
}

@JvmName("mapToUIModelExpenseEntity")
fun Iterable<ExpenseEntity>.mapToUIModel(): List<ExpenseUIModel> {
    return mapNotNull { entity: ExpenseEntity ->
        ExpenseUIModel(
            id = entity.id,
            date = entity.date,
            isPayed = false,
            amount = "${entity.amount} RUB",
            debt = "0 RUB"
        )
    }
}

fun Iterable<Participant>.mapToEntity(tripId: String): List<EntityParticipant> {
    return mapNotNull { uiModel ->
        if (uiModel.id == Const.SELF_ID) null
        else EntityParticipant(
            id = UUID.randomUUID().toString(),
            name = uiModel.name,
            tripId = tripId
        )
    }
}


fun Iterable<Distribution>.mapToEntity(
    expenseId: String,
    debtCalculator: IDebtorCalculator
): List<ExpenseDebtor> {
    val debtorMap = debtCalculator.calculateDebits(this)
    return debtorMap.map { debtorEntry ->
        ExpenseDebtor(
            id = UUID.randomUUID().toString(),
            expenseId = expenseId,
            debtAmount = debtorEntry.value,
            participantId = debtorEntry.key,
            isDebtPayed = false
        )
    }
}

@JvmName("mapToUIModelEntityParticipant")
fun Iterable<EntityParticipant>.mapToUIModel(): List<Participant> {
    return map { Participant(name = it.name, id = it.id) }
}