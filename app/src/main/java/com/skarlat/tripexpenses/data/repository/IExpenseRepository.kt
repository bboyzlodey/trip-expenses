package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfoItem

interface IExpenseRepository {
    suspend fun getExpenses(tripId: String): List<Expense>
    suspend fun putExpense(expense: Expense)
    suspend fun getExpense(expenseId: String): Expense
    suspend fun getExpenseInfoItems(tripId: String): List<ExpenseInfoItem>
    suspend fun removeExpense(expenseId: String)
}