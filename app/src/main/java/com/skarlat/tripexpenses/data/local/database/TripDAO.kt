package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skarlat.tripexpenses.data.local.entity.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDAO {

    @Query("SELECT * FROM Trip")
    suspend fun getTrips(): List<Trip>

    @Query("SELECT * FROM Trip")
    fun getTripsFlow(): Flow<List<Trip>>

    @Insert
    suspend fun insertTrip(trip: Trip)

}