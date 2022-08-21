package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.DebtorDAO
import com.skarlat.tripexpenses.data.local.database.ExpenseDAO
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfoItem
import com.skarlat.tripexpenses.data.local.model.ExpenseWithDebtors
import com.skarlat.tripexpenses.data.local.model.ParticipantExpense
import com.skarlat.tripexpenses.data.local.model.ParticipantId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDAO,
    private val debtorDAO: DebtorDAO
) : IExpenseRepository {

    override suspend fun getExpenses(tripId: String): List<Expense> {
        return withContext(Dispatchers.IO) {
            expenseDao.getExpenses(tripId)
        }
    }

    override suspend fun putExpense(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.insertExpense(expense)
        }
    }

    override suspend fun getExpense(expenseId: String): Expense {
        return expenseDao.getExpense(expenseId)
    }

    override suspend fun getExpenseInfoItems(tripId: String): List<ExpenseInfoItem> {
        return withContext(Dispatchers.IO) {
            expenseDao.getExpenseInfo(tripId)
        }
    }

    override suspend fun removeExpense(expenseId: String) {
        withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(expenseId)
            expenseDao.deleteExpenseDebtors(expenseId)
        }
    }

    override suspend fun getExpensesForTrip(tripId: String): List<ExpenseWithDebtors> {
        class ExpenseWithDebtorsInternal(
            override val expenseId: String,
            override val debtors: List<ParticipantExpense>,
            override val payOwner: ParticipantId
        ) : ExpenseWithDebtors

        return withContext(Dispatchers.IO) {
            val expenses = expenseDao.getExpenses(tripId = tripId)
            val debtorsMap = debtorDAO.getDebtors(expenses.map { it.id }).groupBy { it.expenseId }
            val expensesWithDebtors = expenses.map {
                ExpenseWithDebtorsInternal(
                    expenseId = it.id,
                    payOwner = ParticipantId(it.ownerId),
                    debtors = debtorsMap[it.id]?.map { expenseDebtor ->
                        ParticipantExpense(
                            costAmount = expenseDebtor.debtAmount,
                            participantId = ParticipantId(expenseDebtor.participantId)
                        )
                    } ?: emptyList()
                )
            }
            expensesWithDebtors
        }
    }
}