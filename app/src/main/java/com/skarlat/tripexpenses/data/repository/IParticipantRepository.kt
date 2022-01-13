package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Participant

interface IParticipantRepository {
    suspend fun addParticipant(participant: Participant)
}