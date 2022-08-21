package com.skarlat.tripexpenses.business.calculator

import androidx.annotation.VisibleForTesting
import com.skarlat.tripexpenses.business.model.CalculatedTripBalanceResult
import com.skarlat.tripexpenses.data.local.model.ParticipantId
import com.skarlat.tripexpenses.data.repository.IExpenseRepository
import javax.inject.Inject

class EvaluateTripDebtInteractor @Inject constructor(
    private val expenseRepository: IExpenseRepository
) {

    @VisibleForTesting
    suspend fun calculateBalance(tripId: String): Map<ParticipantId, Int> {
        val expenseList = expenseRepository.getExpensesForTrip(tripId)
        return buildMap {
            expenseList.forEach { expense ->
                expense.debtors.forEach { debtor ->
                    if (debtor.participantId != expense.payOwner) {
                        this[debtor.participantId] =
                            (this[debtor.participantId] ?: 0) - debtor.costAmount
                        this[expense.payOwner] = (this[expense.payOwner] ?: 0) + debtor.costAmount
                    }
                }
            }
        }
    }

    suspend fun calculateTripBalance(tripId: String): CalculatedTripBalanceResult {
        return CalculatedTripBalanceResult(tripId = tripId, calculateBalance(tripId = tripId))
    }
}