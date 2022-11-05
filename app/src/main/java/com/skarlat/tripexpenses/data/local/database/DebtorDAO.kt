package com.skarlat.tripexpenses.data.local.database

import androidx.room.*
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.model.DebtorInfo
import com.skarlat.tripexpenses.data.local.model.DebtorPaidRequest

@Dao
interface DebtorDAO {

    @Insert
    suspend fun insert(entity: ExpenseDebtor)

    @Insert
    suspend fun insertAll(vararg debtors: ExpenseDebtor)

    @Transaction
    @Query("SELECT * FROM expensedebtor WHERE expense_id = :expenseId")
    suspend fun getExpenseDebtors(expenseId: String): List<DebtorInfo>

    @Update(entity = ExpenseDebtor::class)
    suspend fun updateDebtor(request: DebtorPaidRequest)

    // Only for testing
    @Query("SELECT * FROM expensedebtor WHERE id =:debtorId")
    suspend fun getExpenseDebtor(debtorId: String): ExpenseDebtor

    @Transaction
    @Query(
        "SELECT * FROM expensedebtor WHERE expensedebtor.expense_id IN (:expenseIds)"
    )
    suspend fun getDebtors(expenseIds: List<String>): List<ExpenseDebtor>
}