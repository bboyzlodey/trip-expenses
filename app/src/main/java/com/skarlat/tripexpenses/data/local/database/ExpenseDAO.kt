package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfoItem
import com.skarlat.tripexpenses.data.local.model.TripExpenses

@Dao
interface ExpenseDAO {

    @Query("SELECT * FROM expense WHERE trip_id = :tripId")
    suspend fun getExpenses(tripId: String): List<Expense>

    @Transaction
    @Query("SELECT * FROM trip WHERE id =:tripId")
    suspend fun getTripExpenses(tripId: String): TripExpenses

    @Transaction
    @Query("SELECT * FROM expense WHERE expense_id = :expenseId")
    suspend fun getExpense(expenseId: String): Expense

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Transaction
    @Query(
        "SELECT expense.expense_id, expense.description, expense.date," +
                "sum(expensedebtor.debt_amount) AS amount, " +
                "avg(expensedebtor.is_debt_payed) AS is_payed " +
                "FROM expense, expensedebtor " +
                "WHERE expense.trip_id =:tripId " +
                "AND expense.expense_id = expensedebtor.expense_id " +
                "GROUP BY expense.expense_id"
    )
    suspend fun getExpenseInfo(tripId: String): List<ExpenseInfoItem>

    @Insert
    suspend fun insetAll(vararg expenses: Expense)

    @Transaction
    @Query("DELETE FROM expense WHERE expense_id = :id")
    suspend fun deleteExpense(id: String)

    @Transaction
    @Query("DELETE FROM expensedebtor WHERE expense_id = :id")
    suspend fun deleteExpenseDebtors(id: String)
}