package com.skarlat.tripexpenses.data.repository

interface TripRepository {
    suspend fun getTrips()
}