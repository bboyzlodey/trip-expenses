package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.ExpenseDAO
import com.skarlat.tripexpenses.data.local.entity.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDAO) :
    IExpenseRepository {
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
}