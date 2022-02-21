package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Trip
import kotlinx.coroutines.flow.Flow

interface ITripRepository {
    suspend fun getTrips(): List<Trip>
    suspend fun getTrip(tripId: String): Trip
    fun getTripsFlow(): Flow<List<Trip>>
    suspend fun createTrip(trip: Trip)
    suspend fun getTripCostAmount(tripId: String): Int?
}