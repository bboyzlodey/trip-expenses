package com.skarlat.tripexpenses.ui.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface Navigator {
    fun navigateUp(): Boolean
    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit = {}): Boolean
    val destinations: Flow<NavigationEvent>
}