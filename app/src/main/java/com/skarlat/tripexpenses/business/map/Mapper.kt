package com.skarlat.tripexpenses.business.map

import com.skarlat.tripexpenses.data.local.entity.Expense as ExpenseEntity
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
fun Iterable<ExpenseEntity>.mapToUIModel() : List<ExpenseUIModel> {
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