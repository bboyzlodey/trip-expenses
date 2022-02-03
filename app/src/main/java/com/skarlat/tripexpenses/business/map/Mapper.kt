package com.skarlat.tripexpenses.business.map

import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.business.calculator.IDebtorCalculator
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.DateFormatter
import com.skarlat.tripexpenses.utils.StringResourceWrapper
import java.util.*
import com.skarlat.tripexpenses.data.local.entity.Expense as ExpenseEntity
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor as DebtorBusinessModel
import com.skarlat.tripexpenses.data.local.entity.Participant as EntityParticipant
import com.skarlat.tripexpenses.data.local.entity.Trip as TripEntity
import com.skarlat.tripexpenses.data.local.model.DebtorInfo as DebtorInfoBusinessModel
import com.skarlat.tripexpenses.ui.model.Debtor as DebtorUIModel
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
fun Iterable<ExpenseEntity>.mapToUIModel(dateFormatter: DateFormatter): List<ExpenseUIModel> {
    return mapNotNull { entity: ExpenseEntity ->
        ExpenseUIModel(
            id = entity.id,
            date = dateFormatter.formatDateFromISO(entity.date),
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
): List<DebtorBusinessModel> {
    val debtorMap = debtCalculator.calculateDebits(this)
    return debtorMap.map { debtorEntry ->
        DebtorBusinessModel(
            id = UUID.randomUUID().toString(),
            expenseId = expenseId,
            debtAmount = debtorEntry.value,
            participantId = debtorEntry.key,
            isDebtPayed = false
        )
    }
}

@JvmName("mapToUIModelEntityParticipant")
fun Iterable<EntityParticipant>.mapToUIModel(stringResourceWrapper: StringResourceWrapper): List<Participant> {
    return map {
        Participant(
            name = if (it.name == Const.SELF_ID) stringResourceWrapper.getString(R.string.my_self) else it.name,
            id = it.id
        )
    }
}

@JvmName("mapToUIModelExpenseDebtor")
fun Iterable<DebtorInfoBusinessModel>.mapToUIModel(): List<DebtorUIModel> {
    return map {
        DebtorUIModel(
            participant = Participant(
                name = it.participant.name,
                id = it.participant.name
            ),
            amount = it.debtor.debtAmount,
            isPayed = it.debtor.isDebtPayed,
            id = it.debtor.id
        )
    }
}