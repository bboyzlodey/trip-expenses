package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.ExpenseInteractor
import com.skarlat.tripexpenses.business.interactor.TripInteractor
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.model.TripInfo
import com.skarlat.tripexpenses.ui.navigation.CreateExpenseDestination
import com.skarlat.tripexpenses.ui.navigation.ExpenseDestination
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
    private val navigator: Navigator,
    private val tripInteractor: TripInteractor
) : ViewModel() {

    val expensesList: StateFlow<List<Expense>> get() = expensesListFlow
    private val expensesListFlow = MutableStateFlow(emptyList<Expense>())


    val tripInfo: StateFlow<TripInfo> get() = tripInfoFlow
    private val tripInfoFlow =
        MutableStateFlow<TripInfo>(TripInfo(name = "", participantsName = emptyList()))

    private var tripId: String = ""

    fun openTripId(tripId: String) {
        this.tripId = tripId
        loadExpenseItems()
        loadTripInfo()
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

    private fun loadTripInfo() {
        viewModelScope.launch {
            val participants = expenseInteractor.getTripParticipants(tripId)
            val trip = tripInteractor.getTrip(tripId)
            tripInfoFlow.emit(
                TripInfo(
                    name = trip.name,
                    participantsName = participants.map { it.name })
            )
        }
    }

    fun onExpenseClicked(expenseId: String) {
        val destination = ExpenseDestination.createDestination(expenseId)
        navigator.navigate(destination.route())
    }

    fun onCreateExpenseClicked() {
        val destination = CreateExpenseDestination.createDestination(tripId = tripId)
        navigator.navigate(destination.route())
    }
}