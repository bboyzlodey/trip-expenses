package com.skarlat.tripexpenses.ui.navigation

import com.skarlat.tripexpenses.utils.Const

object TripListDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_LIST_TRIP
}

object ExpenseDestination : NavigationDestination {
    private const val argumentKey = "expenseId"

    override fun route(): String = "expense/{$argumentKey}"

    fun createDestination(expenseId: String) = NavigationDestination {
        "expense/${expenseId}"
    }
}

object CreateTripDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_CREATE_TRIP
}

object CreateExpenseDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_CREATE_EXPENSE

    fun createDestination(tripId: String) = NavigationDestination {
        "createExpense/$tripId"
    }
}

object ExpenseListDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_LIST_EXPENSES

    fun createDestination(tripId: String) = NavigationDestination {
        "listExpenses/$tripId"
    }
}
