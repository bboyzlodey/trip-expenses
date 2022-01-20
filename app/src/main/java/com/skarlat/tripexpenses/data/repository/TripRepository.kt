package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.TripDAO
import com.skarlat.tripexpenses.data.local.entity.Trip
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class TripRepository @Inject constructor(private val tripDAO: TripDAO) : ITripRepository {
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