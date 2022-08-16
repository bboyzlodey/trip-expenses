package com.skarlat.tripexpenses.business.interactor

import com.skarlat.tripexpenses.business.calculator.DebtCalculator
import com.skarlat.tripexpenses.business.map.mapToEntity
import com.skarlat.tripexpenses.business.map.mapToUIModel
import com.skarlat.tripexpenses.data.local.model.DebtorPaidRequest
import com.skarlat.tripexpenses.data.repository.IDebtorRepository
import com.skarlat.tripexpenses.data.repository.IExpenseRepository
import com.skarlat.tripexpenses.data.repository.IParticipantRepository
import com.skarlat.tripexpenses.ui.model.CreateExpenseCommand
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.DateFormatter
import com.skarlat.tripexpenses.utils.StringResourceWrapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import javax.inject.Inject
import com.skarlat.tripexpenses.data.local.entity.Expense as ExpenseEntity
import com.skarlat.tripexpenses.ui.model.ExpenseInfo as ExpenseInfoUIModel

@ActivityRetainedScoped
class ExpenseInteractor @Inject constructor(
    private val expenseRepository: IExpenseRepository,
    private val debtorRepository: IDebtorRepository,
    private val participantRepository: IParticipantRepository,
    private val stringResourceWrapper: StringResourceWrapper,
    private val dateFormatter: DateFormatter
) {

    suspend fun createExpense(screenData: CreateExpenseCommand) {
        val expense = ExpenseEntity(
            id = UUID.randomUUID().toString(),
            ownerId = screenData.payOwnerId,
            description = screenData.description,
            tripId = screenData.tripId,
            amount = screenData.totalAmount,
            date = screenData.date
        )
        val debtors = screenData.distributions.mapToEntity(
            expenseId = expense.id,
            DebtCalculator(),
            expense.ownerId
        )
        debtorRepository.addDebtors(debtors)
        expenseRepository.putExpense(expense)
    }

    suspend fun getExpenses(tripId: String): Flow<List<Expense>> {
        return flowOf(
            expenseRepository.getExpenseInfoItems(tripId)
                .sortedByDescending { it.date }
                .mapToUIModel(dateFormatter = dateFormatter)
        )
    }

    suspend fun getTripParticipantsFlow(tripId: String): Flow<List<Participant>> {
        return flow {
            emit(participantRepository.getParticipants(tripId).mapToUIModel(stringResourceWrapper))
        }
    }

    suspend fun getTripParticipants(tripId: String): List<Participant> {
        return participantRepository.getParticipants(tripId).mapToUIModel(stringResourceWrapper)
    }

    suspend fun getExpenseInfo(expenseId: String): ExpenseInfoUIModel {
        val expense = expenseRepository.getExpense(expenseId)
        val debtors = debtorRepository.getDebtors(expenseId)
        var totalAmount = 0
        var totalDebt = 0
        debtors.forEach {
            totalAmount += it.debtor.debtAmount
            if (!it.debtor.isDebtPayed)
                totalDebt += it.debtor.debtAmount
        }
        return ExpenseInfoUIModel(
            description = expense.description,
            debtors = debtors.mapToUIModel(stringResourceWrapper),
            amount = totalAmount,
            debt = totalDebt
        )
    }

    suspend fun markDebtAsPaid(debtId: String) {
        debtorRepository.updateDebtor(DebtorPaidRequest(id = debtId, isDebtPayed = true))
    }

    suspend fun removeExpense(expenseId: String) {
        expenseRepository.removeExpense(expenseId)
    }
}