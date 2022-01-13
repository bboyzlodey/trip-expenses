package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.ExpenseDAO
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseRepository(private val expenseDao: ExpenseDAO) : IExpenseRepository {
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

    override suspend fun getExpenseInfo(expenseId: String): ExpenseInfo {
        return withContext(Dispatchers.IO) {
            expenseDao.getExpenseInfo(expenseId)
        }
    }
}