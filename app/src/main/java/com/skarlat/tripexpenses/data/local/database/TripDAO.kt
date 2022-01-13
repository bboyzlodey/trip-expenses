package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skarlat.tripexpenses.data.local.entity.Trip

@Dao
interface TripDAO {

    @Query("SELECT * FROM Trip")
    suspend fun getTrips(): List<Trip>

    @Insert
    suspend fun insertTrip(trip: Trip)

}