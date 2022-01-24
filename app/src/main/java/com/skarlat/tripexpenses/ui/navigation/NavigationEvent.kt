package com.skarlat.tripexpenses.ui.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationEvent {
    object NavigateUp : NavigationEvent
    class Directions(val destination: String, val builder: NavOptionsBuilder.() -> Unit) :
        NavigationEvent
}