package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Expense

interface IExpenseRepository {
    suspend fun getExpenses(tripId: String): List<Expense>
    suspend fun putExpense(expense: Expense)
    suspend fun getExpense(expenseId: String): Expense
}