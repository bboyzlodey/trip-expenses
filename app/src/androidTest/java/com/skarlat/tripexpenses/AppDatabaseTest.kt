package com.skarlat.tripexpenses

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skarlat.tripexpenses.data.local.database.AppDatabase
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.entity.Participant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var db: AppDatabase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            .setQueryExecutor(testDispatcher.asExecutor())
            .setTransactionExecutor(testDispatcher.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testExpenseInfoRelation() = testScope.runBlockingTest {
        db.clearAllTables()
        val mockedParticipant = Participant("mocked_id", name = "Mr. Mock", "")
        val mockedExpense = Expense(
            "expense_id",
            ownerId = mockedParticipant.id,
            "",
            tripId = mockedParticipant.tripId,
            amount = 0,
            date = ""
        )

        db.participantDAO.insert(mockedParticipant)
        db.expenseDAO.insertExpense(mockedExpense)
        val result = db.expenseDAO.getExpenseInfo(mockedExpense.id)
        assert(result.ownerName == mockedParticipant.name)
    }
}