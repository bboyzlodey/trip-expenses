package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Participant

interface IParticipantRepository {
    suspend fun addParticipants(participants: List<Participant>)
    suspend fun getParticipants(tripId: String): List<Participant>
}