package com.skarlat.tripexpenses.business.interactor

import com.skarlat.tripexpenses.business.calculator.DebtCalculator
import com.skarlat.tripexpenses.business.map.mapToEntity
import com.skarlat.tripexpenses.business.map.mapToUIModel
import com.skarlat.tripexpenses.data.repository.IDebtorRepository
import com.skarlat.tripexpenses.data.repository.IExpenseRepository
import com.skarlat.tripexpenses.data.repository.IParticipantRepository
import com.skarlat.tripexpenses.ui.model.CreateExpenseCommand
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.model.Participant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant
import java.util.*
import com.skarlat.tripexpenses.data.local.entity.Expense as ExpenseEntity

class ExpenseInteractor(
    private val expenseRepository: IExpenseRepository,
    private val debtorRepository: IDebtorRepository,
    private val participantRepository: IParticipantRepository
) {

    private var currentTripId = ""

    suspend fun createExpense(screenData: CreateExpenseCommand) {
        val expense = ExpenseEntity(
            id = UUID.randomUUID().toString(),
            ownerId = screenData.payOwnerId,
            description = "",
            tripId = currentTripId,
            amount = screenData.totalAmount,
            date = Instant.now().toString()
        )
        val debtors = screenData.distributions.mapToEntity(expenseId = expense.id, DebtCalculator())
        debtorRepository.addDebtors(debtors)
        expenseRepository.putExpense(expense)
    }

    suspend fun getExpenses(tripId: String): Flow<List<Expense>> {
        return flowOf(expenseRepository.getExpenses(tripId).mapToUIModel())
    }

    suspend fun getTripParticipants(tripId: String): Flow<List<Participant>> {
        return flow { emit(participantRepository.getParticipants(tripId).mapToUIModel()) }
    }

}