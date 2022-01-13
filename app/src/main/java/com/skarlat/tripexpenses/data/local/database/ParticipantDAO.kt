package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import com.skarlat.tripexpenses.data.local.entity.Participant

@Dao
interface ParticipantDAO {

    @Insert
    suspend fun insert(entity: Participant)

}