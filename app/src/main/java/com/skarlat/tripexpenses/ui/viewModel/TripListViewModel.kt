package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.TripInteractor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TripListViewModel @Inject constructor(tripInteractor: TripInteractor) :
    ViewModel() {

    val tripListFlow = tripInteractor.getTripListFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun onTripCLicked(tripId: String) {

    }

    fun onCreateTripClicked() {

    }
}