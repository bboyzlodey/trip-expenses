package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.TripInteractor
import com.skarlat.tripexpenses.ui.navigation.CreateTripDestination
import com.skarlat.tripexpenses.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    tripInteractor: TripInteractor,
    private val navigator: Navigator
) :
    ViewModel() {

    val tripListFlow = tripInteractor.getTripListFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun onTripCLicked(tripId: String) {

    }

    fun onCreateTripClicked() {
        navigator.navigate(CreateTripDestination.route())
    }

    fun onBackPressed() {
        navigator.navigateUp()
    }
}