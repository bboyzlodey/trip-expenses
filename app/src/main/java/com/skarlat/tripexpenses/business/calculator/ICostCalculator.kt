package com.skarlat.tripexpenses.business.calculator

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface ICostCalculator {
    val totalCost: Flow<Int>

    suspend fun onCostChanged(costId: String, newCost: String)
}

@ViewModelScoped
class TotalCostCalculator @Inject constructor() : ICostCalculator {

    override val totalCost: Flow<Int>
        get() = totalCostMutableFlow

    private val totalCostMutableFlow = MutableStateFlow(0)
    private val costMap = mutableMapOf<String, Int>()

    override suspend fun onCostChanged(costId: String, cost: String) {
        val newCost = cost.toIntOrNull() ?: 0
        val totalCost = this.totalCostMutableFlow.value
        val oldCost = costMap.getOrDefault(costId, 0)
        costMap[costId] = newCost
        totalCostMutableFlow.value = totalCost - oldCost + newCost
    }
}