package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Trip
import kotlinx.coroutines.flow.Flow

interface ITripRepository {
    suspend fun getTrips(): List<Trip>
    fun getTripsFlow(): Flow<List<Trip>>
    suspend fun createTrip(trip: Trip)
}