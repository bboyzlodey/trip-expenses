package com.skarlat.tripexpenses.business.calculator

import com.skarlat.tripexpenses.ui.model.Distribution
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

interface IDebtorCalculator {

    /**
     *  Return map by participant id with total debt for this participant
     * */
    fun calculateDebits(distributions: Iterable<Distribution>): Map<String, Int>
}

@ActivityRetainedScoped
class DebtCalculator @Inject constructor() : IDebtorCalculator {

    override fun calculateDebits(distributions: Iterable<Distribution>): Map<String, Int> {
        val debtorMap = mutableMapOf<String, Int>()
        distributions.forEach { distribution ->
            if (distribution.participantIds.size == 0 || distribution.cost.value == 0)
                return@forEach
            val debtForEach = distribution.cost.value / distribution.participantIds.size
            distribution.participantIds.forEach { participantId ->
                debtorMap.merge(
                    participantId,
                    debtForEach
                ) { oldValue, newValue -> oldValue + newValue }
            }
        }
        return debtorMap
    }

}