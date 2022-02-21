package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.TripDAO
import com.skarlat.tripexpenses.data.local.entity.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepository @Inject constructor(private val tripDAO: TripDAO) : ITripRepository {

    override suspend fun getTrips(): List<Trip> {
        return withContext(Dispatchers.IO) {
            tripDAO.getTrips()
        }
    }

    override suspend fun getTrip(tripId: String): Trip {
        return withContext(Dispatchers.IO) {
            tripDAO.getTrip(tripId)
        }
    }

    override fun getTripsFlow(): Flow<List<Trip>> {
        return tripDAO.getTripsFlow().flowOn(Dispatchers.IO)
    }

    override suspend fun createTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            tripDAO.insertTrip(trip)
        }
    }

    override suspend fun getTripCostAmount(tripId: String): Int? {
        return withContext(Dispatchers.IO) {
            tripDAO.getTripCostAmount(tripId)
        }
    }
}