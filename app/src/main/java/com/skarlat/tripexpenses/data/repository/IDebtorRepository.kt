package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.model.DebtorInfo
import com.skarlat.tripexpenses.data.local.model.DebtorPaidRequest

interface IDebtorRepository {
    suspend fun addDebtor(debtor: ExpenseDebtor)
    suspend fun addDebtors(debtors: List<ExpenseDebtor>)
    suspend fun getDebtors(expenseId: String): List<DebtorInfo>
    suspend fun updateDebtor(request: DebtorPaidRequest)
}