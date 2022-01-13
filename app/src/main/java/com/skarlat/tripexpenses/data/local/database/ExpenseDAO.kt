package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.model.ExpenseInfo
import com.skarlat.tripexpenses.data.local.model.TripExpenses

@Dao
interface ExpenseDAO {

    @Query("SELECT * FROM expense WHERE trip_id = :tripId")
    suspend fun getExpenses(tripId: String) : List<Expense>

    @Transaction
    @Query("SELECT * FROM trip WHERE id =:tripId")
    suspend fun getTripExpenses(tripId: String) : TripExpenses

    @Transaction
    @Query("SELECT * FROM expense WHERE expense_id = :expenseId")
    suspend fun getExpenseInfo(expenseId: String) : ExpenseInfo

    @Insert
    suspend fun insertExpense(expense: Expense)
}