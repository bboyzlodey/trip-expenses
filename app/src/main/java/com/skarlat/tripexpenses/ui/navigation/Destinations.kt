package com.skarlat.tripexpenses.ui.navigation

import com.skarlat.tripexpenses.utils.Const

object TripListDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_LIST_TRIP
}

object CreateTripDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_CREATE_TRIP
}

object CreateExpenseDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_CREATE_EXPENSE
}

object ExpenseListDestination : NavigationDestination {
    override fun route(): String = Const.SCREEN_LIST_EXPENSES

    fun createDestination(tripId: String) = NavigationDestination {
        "listExpenses/$tripId"
    }
}
