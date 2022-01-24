package com.skarlat.tripexpenses.ui.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorImpl @Inject constructor() : Navigator {

    private val navigationEvents = Channel<NavigationEvent>()
    override val destinations: Flow<NavigationEvent>
        get() = navigationEvents.receiveAsFlow()

    override fun navigateUp(): Boolean =
        navigationEvents.trySend(NavigationEvent.NavigateUp).isSuccess

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit): Boolean {
        return navigationEvents.trySend(
            NavigationEvent.Directions(
                destination = route,
                builder = builder
            )
        ).isSuccess
    }
}