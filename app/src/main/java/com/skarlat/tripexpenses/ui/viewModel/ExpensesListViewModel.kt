package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.ExpenseInteractor
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    private val expenseInteractor: ExpenseInteractor,
    private val navigator: Navigator
) : ViewModel() {

    val expensesList: StateFlow<List<Expense>> get() = expensesListFlow
    private val expensesListFlow = MutableStateFlow(emptyList<Expense>())

    private var tripId: String = ""

    fun openTripId(tripId: String) {
        this.tripId = tripId
        loadExpenseItems()
    }

    private fun loadExpenseItems() {
        viewModelScope.launch {
            expenseInteractor
                .getExpenses(tripId)
                .collect {
                    expensesListFlow.emit(it)
                }
        }
    }

    fun onExpenseClicked(expenseId: String) {

    }

    fun onCreateExpenseClicked() {

    }
}