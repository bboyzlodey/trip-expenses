package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Trip

interface ITripRepository {
    suspend fun getTrips() : List<Trip>
    suspend fun createTrip(trip: Trip)
}