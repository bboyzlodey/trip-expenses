package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.ExpenseInteractor
import com.skarlat.tripexpenses.ui.model.ExpenseInfo
import com.skarlat.tripexpenses.ui.model.emptyExpenseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseInteractor: ExpenseInteractor
) : ViewModel() {

    val expenseInfo: StateFlow<ExpenseInfo> get() = expenseInfoFlow
    private val expenseInfoFlow = MutableStateFlow(emptyExpenseInfo)
    private var expenseId = ""

    fun openExpense(expenseId: String) {
        this.expenseId = expenseId
        viewModelScope.launch {
            loadExpense()
        }
    }

    private suspend fun loadExpense() {
        val expenseInfo = expenseInteractor.getExpenseInfo(expenseId)
        expenseInfoFlow.emit(expenseInfo)
    }

    fun markDebtAsPaid(debtId: String) {
        viewModelScope.launch {
            expenseInteractor.markDebtAsPaid(debtId)
            loadExpense()
        }
    }
}