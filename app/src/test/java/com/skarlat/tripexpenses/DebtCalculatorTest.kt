package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.business.calculation.DebtCalculator
import com.skarlat.tripexpenses.business.calculation.IDebtorCalculator
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.utils.Const
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class DebtCalculatorTest {

    private val debtCalculator: IDebtorCalculator = DebtCalculator()

    @After
    fun tearDown() {
        Distribution.resetCounter()
    }

    @Test
    fun calculation_isCorrect() {
        val participantIdSelf = Const.SELF_ID
        val participantIdVasili = "Vasili"
        val participantIdSvetlana = "Svetlana"

        val distributions = listOf<Distribution>(
            Distribution.create(100, participantIds = listOf(participantIdSelf)),
            Distribution.create(
                300,
                participantIds = listOf(participantIdSelf, participantIdSvetlana)
            ),
            Distribution.create(
                250,
                participantIds = listOf(participantIdVasili, participantIdSvetlana)
            ),
            Distribution.create(
                250,
                participantIds = listOf(
                    participantIdSelf,
                    participantIdVasili,
                    participantIdSvetlana
                )
            ),
        )
        val expectedResult = sortedMapOf<String, Int>(
            participantIdSvetlana to 150 + 125 + (250 / 3),
            participantIdSelf to 100 + 150 + (250 / 3),
            participantIdVasili to 125 + (250 / 3)
        )
        val factResult = debtCalculator.calculateDebits(distributions).toSortedMap()
        assertEquals(factResult, expectedResult)
    }
}