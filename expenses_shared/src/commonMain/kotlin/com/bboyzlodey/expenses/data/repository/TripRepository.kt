package com.bboyzlodey.expenses.data.repository

interface TripRepository {
    suspend fun getTripById(tripId: String)
}

internal class TripRepositoryImpl : TripRepository {
    override suspend fun getTripById(tripId: String) {

    }
}