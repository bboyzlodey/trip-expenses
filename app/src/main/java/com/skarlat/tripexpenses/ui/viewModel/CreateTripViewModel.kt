package com.skarlat.tripexpenses.ui.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.TripInteractor
import com.skarlat.tripexpenses.ui.model.Participant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTripViewModel @Inject constructor(private val tripInteractor: TripInteractor) :
    ViewModel() {

    private val tripParticipantsFlow = mutableStateListOf<Participant>()
    private val tripNameFlow = mutableStateOf("")

    val tripName: MutableState<String> get() = tripNameFlow
    val participants: SnapshotStateList<Participant> get() = tripParticipantsFlow

    fun onCreateTripClicked() {
        viewModelScope.launch {
            val tripName = tripNameFlow.value
            if (tripName.isBlank()) return@launch showEmptyTripNameError()
            tripInteractor.createTrip(tripName, participants = tripParticipantsFlow)
            navigateBack()
        }
    }

    fun onTripNameChanged(tripName: String) {
        tripNameFlow.value = tripName
    }

    fun onAddParticipantClicked() {
        tripParticipantsFlow.add(getEmptyParticipant())
    }

    fun onRemoveParticipantClicked(id: String) {
        tripParticipantsFlow.removeAll { it.id == id }
    }

    fun onParticipantNameChanged(id: String, name: String) {
        tripParticipantsFlow.replaceAll { if (it.id == id) it.copy(name = name) else it }
    }

    private fun getEmptyParticipant() = Participant(id = UUID.randomUUID().toString(), name = "")

    private fun navigateBack() {

    }

    private fun showEmptyTripNameError() {

    }
}