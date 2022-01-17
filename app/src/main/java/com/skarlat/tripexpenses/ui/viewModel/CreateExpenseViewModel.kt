package com.skarlat.tripexpenses.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.business.interactor.ExpenseInteractor
import com.skarlat.tripexpenses.ui.ExpenseDateMemento
import com.skarlat.tripexpenses.ui.model.CreateExpenseCommand
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.DialogData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateExpenseViewModel(
    private val expenseInteractor: ExpenseInteractor,
    private val mainViewModel: MainViewModel,
    private val expenseDateMemento: ExpenseDateMemento
) : ViewModel() {

    val expenseDate: StateFlow<String>
        get() = expenseDateMemento.readableDateFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

    val expenseSummaryCost: StateFlow<Int> get() = expenseSummaryCostMutableFlow
    private val expenseSummaryCostMutableFlow = MutableStateFlow(0)

    val expenseParticipantIds: Flow<List<String>> get() = expenseParticipantIdsMutableFlow
    private val expenseParticipantIdsMutableFlow =
        MutableStateFlow<List<String>>(listOf())

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

    fun onSummaryCostChanged(cost: String) {
        val summaryCost = cost.toIntOrNull() ?: 0
        expenseSummaryCostMutableFlow.tryEmit(summaryCost)
    }

    fun onParticipantOfExpenseClicked(item: Participant) {
        viewModelScope.launch {
            val participants = expenseParticipantIds.first()
            expenseParticipantIdsMutableFlow.emit(participants.addOrRemove(item.id))
        }
    }

    fun onSelectDateClicked() {
        viewModelScope.launch {
            mainViewModel.showDialog(
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
        distributionsMutable.add(getNextDistribution())
    }

    fun onRemoveDistributionCLicked(distribution: Distribution) {
        distributionsMutable.removeIf { it.id == distribution.id }
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
            payOwnerId = payOwnerId.value
        )
    }

    private fun expenseCreatedSuccess() {
        // TODO
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