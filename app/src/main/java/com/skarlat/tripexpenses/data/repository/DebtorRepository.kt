package com.skarlat.tripexpenses.data.repository

import com.skarlat.tripexpenses.data.local.database.DebtorDAO
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.model.DebtorInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebtorRepository @Inject constructor(private val debtorDAO: DebtorDAO) : IDebtorRepository {
    override suspend fun addDebtor(debtor: ExpenseDebtor) {
        withContext(Dispatchers.IO) {
            debtorDAO.insert(debtor)
        }
    }

    override suspend fun addDebtors(debtors: List<ExpenseDebtor>) {
        withContext(Dispatchers.IO) {
            debtorDAO.insertAll(*debtors.toTypedArray())
        }
    }

    override suspend fun getDebtors(expenseId: String): List<DebtorInfo> {
        return withContext(Dispatchers.IO) {
            debtorDAO.getExpenseDebtors(expenseId)
        }
    }
}