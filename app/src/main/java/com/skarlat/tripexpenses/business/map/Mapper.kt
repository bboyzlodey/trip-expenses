package com.skarlat.tripexpenses.business.map

import com.skarlat.tripexpenses.data.local.entity.Trip

fun Iterable<Trip>.mapToUIModel(): List<com.skarlat.tripexpenses.ui.model.Trip> {
    return mapNotNull { trip: Trip ->
        com.skarlat.tripexpenses.ui.model.Trip(
            id = trip.id,
            name = trip.name
        )
    }
}