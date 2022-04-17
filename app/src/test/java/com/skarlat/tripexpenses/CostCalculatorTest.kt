package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.business.calculation.ICostCalculator
import com.skarlat.tripexpenses.business.calculation.TotalCostCalculator
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CostCalculatorTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val costCalculator: ICostCalculator = TotalCostCalculator()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun totalCost() = runTest(context = UnconfinedTestDispatcher(testScheduler)) {
        val costChangesHistory = listOf<Pair<String, Int>>(
            "1" to 10,
            "1" to 0,
            "4" to 1000,
            "5" to 600,
            "5" to 500,
            "6" to 100
        )
        var costCalculationResult = -1

        val job = launch {
            costCalculator.totalCost.collect {
                print("cost $it")
                costCalculationResult = it
            }
        }
        costChangesHistory.forEach {
            costCalculator.onCostChanged(it.first, it.second.toString())
        }
        job.cancel()
        val actualResult = 1600
        val factResult = costCalculationResult
        assertEquals(factResult, actualResult)
    }
}