package com.skarlat.tripexpenses.business.interactor

import com.skarlat.tripexpenses.business.map.mapToEntity
import com.skarlat.tripexpenses.business.map.mapToUIModel
import com.skarlat.tripexpenses.data.local.entity.Trip
import com.skarlat.tripexpenses.data.repository.IParticipantRepository
import com.skarlat.tripexpenses.data.repository.ITripRepository
import com.skarlat.tripexpenses.ui.model.Participant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class TripInteractor(
    private val tripRepository: ITripRepository,
    private val participantRepository: IParticipantRepository
) {

    suspend fun createTrip(name: String, participants: List<Participant>) {
        val newTrip = generateTrip(name)
        tripRepository.createTrip(newTrip)
        participantRepository.addParticipants(participants.mapToEntity(newTrip.id))
    }

    private suspend fun generateTrip(tripName: String): Trip = Trip(
        id = UUID.randomUUID().toString(),
        totalCost = 0,
        personalCost = 0,
        debt = 0,
        name = tripName
    )

    suspend fun getTripList(): Flow<List<com.skarlat.tripexpenses.ui.model.Trip>> {
        return flowOf(tripRepository.getTrips().mapToUIModel())
    }
}