package com.skarlat.tripexpenses.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.MockHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CreateExpenseViewModel : ViewModel() {

    val expenseDate: StateFlow<String> get() = expenseDateMutableFlow
    private val expenseDateMutableFlow = MutableStateFlow("")

    val expenseSummaryCost: StateFlow<Int> get() = expenseSummaryCostMutableFlow
    private val expenseSummaryCostMutableFlow = MutableStateFlow(0)

    val expenseParticipantIds: Flow<List<String>> get() = expenseParticipantIdsMutableFlow
    private val expenseParticipantIdsMutableFlow =
        MutableStateFlow<List<String>>(listOf())

    val participants: StateFlow<List<Participant>> get() = participantsMutableFlow
    private val participantsMutableFlow =
        MutableStateFlow<List<Participant>>(
            MockHelper.getParticipants()
        )

    val participantCosts: Flow<List<Pair<Participant, Int>>> get() = participantCostsMutableFlow
    private val participantCostsMutableFlow =
        MutableStateFlow<List<Pair<Participant, Int>>>(listOf())

    val payOwnerId: StateFlow<String> get() = payOwnerIdMutable
    private val payOwnerIdMutable = MutableStateFlow(Const.SELF_ID)

    val distribution: SnapshotStateList<Distribution> get() = distributionsMutable
    private val distributionsMutable = mutableStateListOf(getNextDistribution())

    fun onDateSelected(dayOfMonth: Int, monthOfYear: Int, year: Int) {
        viewModelScope.launch {
            expenseDateMutableFlow.emit("${dayOfMonth}-${monthOfYear}-${year}")
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

    inline fun List<String>.addOrRemove(key: String): List<String> {
        return this.toMutableList().apply {
            if (!this.removeIf { it == key })
                this.add(key)
        }
    }

    fun onSelectDateClicked() {

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

    private var distributionCounter = 0

    private fun getNextDistribution(): Distribution {
        return Distribution(
            id = distributionCounter++,
            cost = mutableStateOf(0),
            participantIds = mutableStateListOf()
        )
    }

    override fun onCleared() {
        super.onCleared()
        distributionCounter = 0
    }
}