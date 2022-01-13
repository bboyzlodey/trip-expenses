package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.model.DebtorInfo

@Dao
interface DebtorDAO {

    @Insert
    suspend fun insert(entity: ExpenseDebtor)

    @Transaction
    @Query("SELECT * FROM expensedebtor WHERE expense_id = :expenseId")
    suspend fun getExpenseDebtors(expenseId: String) : List<DebtorInfo>
}