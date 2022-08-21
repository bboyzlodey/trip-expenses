package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skarlat.tripexpenses.data.local.entity.Participant
import com.skarlat.tripexpenses.utils.Const
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDAO {

    @Insert
    suspend fun insert(entity: Participant)

    @Insert
    suspend fun insertAll(vararg participants: Participant)

    @Query("SELECT * FROM participant WHERE trip_id = :tripId OR trip_id = '${Const.ALL_TRIPS}'")
    suspend fun getParticipants(tripId: String): List<Participant>

    @Query("SELECT * FROM participant")
    fun getParticipantsFlow(): Flow<List<Participant>>
}