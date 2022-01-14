package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skarlat.tripexpenses.data.local.entity.Participant

@Dao
interface ParticipantDAO {

    @Insert
    suspend fun insert(entity: Participant)

    @Insert
    suspend fun insertAll(vararg participants: Participant)

    @Query("SELECT * FROM participant WHERE trip_id = :tripId")
    suspend fun getParticipants(tripId: String) : List<Participant>
}