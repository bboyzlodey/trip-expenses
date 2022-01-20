package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.ParticipantDAO
import com.skarlat.tripexpenses.data.local.entity.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParticipantRepository @Inject constructor(private val participantDAO: ParticipantDAO) :
    IParticipantRepository {
    override suspend fun addParticipants(participants: List<Participant>) {
        withContext(Dispatchers.IO) {
            participantDAO.insertAll(*participants.toTypedArray())
        }
    }

    override suspend fun getParticipants(tripId: String): List<Participant> {
        return withContext(Dispatchers.IO) {
            getParticipants(tripId)
        }
    }
}