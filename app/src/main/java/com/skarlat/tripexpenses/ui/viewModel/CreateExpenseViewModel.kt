package com.skarlat.tripexpenses.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.ActivityUtils
import com.skarlat.tripexpenses.business.calculator.ICostCalculator
import com.skarlat.tripexpenses.business.interactor.ExpenseInteractor
import com.skarlat.tripexpenses.ui.ExpenseDateMemento
import com.skarlat.tripexpenses.ui.model.CreateExpenseCommand
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.ui.navigation.Navigator
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.DialogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val expenseInteractor: ExpenseInteractor,
    private val activityUtils: ActivityUtils,
    private val expenseDateMemento: ExpenseDateMemento,
    private val costCalculator: ICostCalculator,
    private val navigator: Navigator
) : ViewModel() {

    var tripId by Delegates.observable("") { _, _, _ ->
        loadParticipants()
    }

    val expenseDate: StateFlow<String>
        get() = expenseDateMemento.readableDateFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    val expenseSummaryCost: StateFlow<Int>
        get() = costCalculator.totalCost.stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            0
        )

    val expenseDescription: StateFlow<String> get() = expenseDescriptionFlow
    private val expenseDescriptionFlow = MutableStateFlow("")
//    val expenseParticipantIds: Flow<List<String>> get() = expenseParticipantIdsMutableFlow
//    private val expenseParticipantIdsMutableFlow =
//        MutableStateFlow<List<String>>(listOf())

    val participants: StateFlow<List<Participant>> get() = participantsMutableFlow
    private val participantsMutableFlow = MutableStateFlow<List<Participant>>(emptyList())

    val payOwnerId: StateFlow<String> get() = payOwnerIdMutable
    private val payOwnerIdMutable = MutableStateFlow(Const.SELF_ID)

    val distribution: SnapshotStateList<Distribution> get() = distributionsMutable
    private val distributionsMutable = mutableStateListOf(getNextDistribution())

    private fun onDateInMillisSelected(dateInMillis: Long) {
        viewModelScope.launch {
            expenseDateMemento.selectTimestamp(dateInMillis)
        }
    }

    private fun loadParticipants() {
        viewModelScope.launch {
            expenseInteractor.getTripParticipantsFlow(tripId)
                .collect { participantsMutableFlow.emit(it) }
        }
    }

    fun onCostChanged(cost: String, costId: String) {
        viewModelScope.launch {
            costCalculator.onCostChanged(costId, cost)
        }
    }

    fun onExpenseDescriptionChanged(newDescription: String) {
        expenseDescriptionFlow.value = newDescription
    }

    @Deprecated("Unused method")
    fun onParticipantOfExpenseClicked(item: Participant) {
        viewModelScope.launch {
//            val participants = expenseParticipantIds.first()
//            expenseParticipantIdsMutableFlow.emit(participants.addOrRemove(item.id))
        }
    }

    fun onSelectDateClicked() {
        viewModelScope.launch {
            activityUtils.showDialog(
                DialogData.DatePicker(
                    onDateSelected = { onDateInMillisSelected(it) },
                    negativeButtonClicked = {}
                )
            )
        }
    }

    fun onPayOwnerSelected(id: String) {
        payOwnerIdMutable.tryEmit(id)
    }

    fun onAddDistributionClicked() {
        val nextDistribution = getNextDistribution()
        distributionsMutable.add(nextDistribution)
    }

    fun onRemoveDistributionCLicked(distribution: Distribution) {
        viewModelScope.launch {
            distributionsMutable.removeIf { it.id == distribution.id }
            costCalculator.onCostChanged(distribution.id.toString(), "0")
        }
    }

    private fun getNextDistribution(): Distribution {
        return Distribution.createEmpty()
    }

    override fun onCleared() {
        super.onCleared()
        Distribution.resetCounter()
    }

    fun onCreateExpenseClicked() {
        viewModelScope.launch {
            val screenData = packScreenData()
            flow { emit(expenseInteractor.createExpense(screenData)) }
                .onStart { }
                .onCompletion { }
                .catch { error -> expenseCreatedFailure(error) }
                .collect { expenseCreatedSuccess() }
        }
    }

    private suspend fun packScreenData(): CreateExpenseCommand {
        return CreateExpenseCommand(
            date = expenseDateMemento.getSelectedDateISO(),
            totalAmount = expenseSummaryCost.value,
            distributions = distribution,
            payOwnerId = payOwnerId.value,
            tripId = tripId,
            description = expenseDescriptionFlow.value
        )
    }

    private fun expenseCreatedSuccess() {
        navigator.navigateUp()
    }

    private fun expenseCreatedFailure(error: Throwable?) {
        // TODO
    }

    private fun List<String>.addOrRemove(key: String): List<String> {
        return this.toMutableList().apply {
            if (!this.removeIf { it == key })
                this.add(key)
        }
    }
}