package com.skarlat.tripexpenses.ui.mapper

import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.business.model.CalculatedTripBalanceResult
import com.skarlat.tripexpenses.data.repository.NameDictionary
import com.skarlat.tripexpenses.ui.model.TripCalculationResultModel
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.StringResourceWrapper
import javax.inject.Inject

class CalculationTripResultMapper @Inject constructor(
    private val nameDictionary: NameDictionary,
    private val stringResourceWrapper: StringResourceWrapper
) {

    private val defaultTripNameMask = stringResourceWrapper.getString(R.string.trip_name_mask)
    private val defaultSelfName = stringResourceWrapper.getString(R.string.my_self)

    fun mapCalculationTripResult(
        result: CalculatedTripBalanceResult,
        tripNameMask: String = defaultTripNameMask,
        selfName: String = defaultSelfName
    ): TripCalculationResultModel {
        val tripName = nameDictionary[result.tripId] ?: "[none]"
        val title = kotlin.runCatching {
            tripNameMask.format(tripName)
        }
            .recoverCatching { defaultTripNameMask.format(tripName) }
            .getOrDefault(tripName)
        return TripCalculationResultModel(
            title = title,
            balances = result.participantBalanceMap.map {
                val participantId = it.key.id
                val participantName =
                    if (participantId == Const.SELF_ID) selfName else nameDictionary[participantId]
                        ?: participantId
                "$participantName: ${it.value}"
            })
    }

}