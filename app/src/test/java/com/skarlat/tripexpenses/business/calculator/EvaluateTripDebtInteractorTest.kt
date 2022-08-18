package com.skarlat.tripexpenses.business.calculator

import com.skarlat.tripexpenses.business.readObjectFromJsonResource
import com.skarlat.tripexpenses.data.local.model.ExpenseWithDebtors
import com.skarlat.tripexpenses.data.local.model.ParticipantExpense
import com.skarlat.tripexpenses.data.local.model.ParticipantId
import com.skarlat.tripexpenses.data.repository.IExpenseRepository
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalStdlibApi::class)
class EvaluateTripDebtInteractorTest : CoroutineEnvironment by CoroutineEnvironmentImpl() {

    private val expenseRepository: IExpenseRepository = mock()
    private val interactor: EvaluateTripDebtInteractor =
        EvaluateTripDebtInteractor(expenseRepository)
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    @Test
    fun threePersonsTest() = launchTest {
        val jsonResurce = "expense_test_1.json"
        val expensesList = moshi.readObjectFromJsonResource<List<ParcelableExpense>>(jsonResurce)
        whenever(expenseRepository.getExpensesForTrip("")).thenReturn(expensesList)
        val factResult = interactor.calculateBalance("")
        Assert.assertEquals(
            factResult, mapOf(
                ParticipantId("self") to 400,
                ParticipantId("ivan") to -600,
                ParticipantId("maria") to 200
            )
        )
    }

    class ParcelableExpense(
        @Json(name = "expense_id")
        override val expenseId: String,
        @Json(name = "debtors")
        private val map: Map<String, Int>,
        @Json(name = "pay_owner")
        private val ownerId: String
    ) : ExpenseWithDebtors {

        @Json(ignore = true)
        override val debtors: List<ParticipantExpense> = map.map {
            ParticipantExpense(
                participantId = ParticipantId(id = it.key),
                costAmount = it.value
            )
        }

        @Json(ignore = true)
        override val payOwner: ParticipantId = ParticipantId(id = ownerId)
    }

}