package com.skarlat.tripexpenses.business.interactor

import com.skarlat.tripexpenses.business.map.mapToEntity
import com.skarlat.tripexpenses.business.map.mapToUIModel
import com.skarlat.tripexpenses.data.local.entity.Trip
import com.skarlat.tripexpenses.data.repository.IParticipantRepository
import com.skarlat.tripexpenses.data.repository.ITripRepository
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.Const
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import com.skarlat.tripexpenses.data.local.entity.Participant as ParticipantEntity

@ActivityRetainedScoped
class TripInteractor @Inject constructor(
    private val tripRepository: ITripRepository,
    private val participantRepository: IParticipantRepository,
) {

    suspend fun createTrip(name: String, participants: List<Participant>) {
        val newTrip = generateTrip(name)
        tripRepository.createTrip(newTrip)
        participantRepository.addParticipants(
            participants = participants
                .mapToEntity(newTrip.id) + ParticipantEntity(
                name = Const.SELF_ID,
                id = UUID.randomUUID().toString(),
                tripId = newTrip.id
            )
        )
    }

    private suspend fun generateTrip(tripName: String): Trip = Trip(
        id = UUID.randomUUID().toString(),
        totalCost = 0,
        personalCost = 0,
        debt = 0,
        name = tripName
    )

    fun getTripListFlow(): Flow<List<com.skarlat.tripexpenses.ui.model.Trip>> {
        return tripRepository.getTripsFlow().map { it.mapToUIModel() }
    }

    suspend fun getTrip(tripId: String): Trip {
        return tripRepository.getTrip(tripId)
    }
}