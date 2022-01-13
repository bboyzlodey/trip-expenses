package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.TripDAO
import com.skarlat.tripexpenses.data.local.entity.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository(private val tripDAO: TripDAO) : ITripRepository {
    override suspend fun getTrips(): List<Trip> {
        return withContext(Dispatchers.IO) {
            tripDAO.getTrips()
        }
    }

    override suspend fun createTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            tripDAO.insertTrip(trip)
        }
    }
}