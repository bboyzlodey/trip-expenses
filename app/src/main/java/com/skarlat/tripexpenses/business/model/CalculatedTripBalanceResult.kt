package com.skarlat.tripexpenses.business.model

import com.skarlat.tripexpenses.data.local.model.ParticipantId

class CalculatedTripBalanceResult(
    val tripId: String,
    val participantBalanceMap: Map<ParticipantId, Int>
)