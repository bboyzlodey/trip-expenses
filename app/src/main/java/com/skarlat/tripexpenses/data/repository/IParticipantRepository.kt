package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Participant
import kotlinx.coroutines.flow.Flow

interface IParticipantRepository {
    suspend fun addParticipants(participants: List<Participant>)
    suspend fun getParticipants(tripId: String): List<Participant>
    fun getParticipantsFlow(): Flow<List<Participant>>
}