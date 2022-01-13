package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfo

interface IExpenseRepository {
    suspend fun getExpenses(tripId: String): List<Expense>
    suspend fun putExpense(expense: Expense)
    suspend fun getExpenseInfo(expenseId: String): ExpenseInfo
}