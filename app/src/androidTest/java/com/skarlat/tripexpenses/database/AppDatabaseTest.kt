package com.skarlat.tripexpenses.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skarlat.tripexpenses.core.CoroutineEnvironment
import com.skarlat.tripexpenses.core.CoroutineEnvironmentImpl
import com.skarlat.tripexpenses.data.local.database.AppDatabase
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.entity.Participant
import com.skarlat.tripexpenses.data.local.model.DebtorInfo
import com.skarlat.tripexpenses.data.local.model.DebtorPaidRequest
import com.skarlat.tripexpenses.data.local.model.ExpenseInfoItem
import com.skarlat.tripexpenses.di.DatabaseModule
import com.skarlat.tripexpenses.utils.Const
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : CoroutineEnvironment by CoroutineEnvironmentImpl() {
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            .setQueryExecutor(executor)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    DatabaseModule.AddSelfIdCallback.onCreate(db)
                }
            })
            .setTransactionExecutor(executor)
            .fallbackToDestructiveMigration()
            .build()
    }

    @After
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun debtorInfoRelation() = launchTest {
        val mockedParticipant = Participant("mocked_id", name = "Mr. Mock", "")
        val mockedDebtor = ExpenseDebtor(
            "id",
            expenseId = "some_expense",
            1000,
            participantId = mockedParticipant.id,
            isDebtPayed = false
        )
        db.participantDAO.insert(mockedParticipant)
        db.debtorDAO.insert(mockedDebtor)

        val factResult = db.debtorDAO.getExpenseDebtors(mockedDebtor.expenseId)
        val expectedResult = listOf(
            DebtorInfo(debtor = mockedDebtor, participant = mockedParticipant)
        )
        assert(factResult == expectedResult)
    }

    @Test
    fun expenseInfoItem_query_Test() = launchTest {
        // mock
        val mockedParticipantVasya = Participant("vasya", "Василий", tripId = "sorochany")
        val mockedParticipantHelga = Participant("helga", "Ольга", tripId = "sorochany")
        val mockedParticipantOleg = Participant("oleg", "Олег", tripId = "rosa_khutor")
        val mockedParticipants =
            listOf(mockedParticipantHelga, mockedParticipantOleg, mockedParticipantVasya)
        val mockedExpenses = listOf<Expense>(
            Expense(
                "cafe1",
                ownerId = "",
                description = "Кафе1",
                tripId = "sorochany",
                amount = 0,
                date = "2022-01-15"
            ),
            Expense(
                "cafe2",
                ownerId = "",
                description = "Кафе2",
                tripId = "sorochany",
                amount = 0,
                date = "2022-01-16"
            ),
            Expense(
                "cafe3",
                ownerId = "",
                description = "Кафе3",
                tripId = "sorochany",
                amount = 0,
                date = "2022-01-17"
            ),
            Expense(
                "cafe4",
                ownerId = "",
                description = "Кафе4",
                tripId = "rosa_khutor",
                amount = 0,
                date = "2022-01-15"
            ),
        )
        val mockedDebtors = listOf(
            ExpenseDebtor(
                id = "1",
                expenseId = "cafe1",
                debtAmount = 100,
                participantId = "vasya",
                isDebtPayed = true
            ),
            ExpenseDebtor(
                id = "2",
                expenseId = "cafe2",
                debtAmount = 100,
                participantId = "vasya",
                isDebtPayed = false
            ),
            ExpenseDebtor(
                id = "3",
                expenseId = "cafe2",
                debtAmount = 100,
                participantId = "helga",
                isDebtPayed = true
            ),
            ExpenseDebtor(
                id = "4",
                expenseId = "cafe3",
                debtAmount = 100,
                participantId = "vasya",
                isDebtPayed = false
            ),

            ExpenseDebtor(
                id = "5",
                expenseId = "cafe4",
                debtAmount = 1000,
                participantId = "oleg",
                isDebtPayed = false
            ),
        )

        db.participantDAO.insertAll(*mockedParticipants.toTypedArray())
        db.expenseDAO.insetAll(*mockedExpenses.toTypedArray())
        db.debtorDAO.insertAll(*mockedDebtors.toTypedArray())

        // action
        val factResult = db.expenseDAO.getExpenseInfo("sorochany")

        // verify
        val actualResult = listOf(
            ExpenseInfoItem(
                expenseId = "cafe1",
                isPayed = true,
                totalAmount = 100,
                date = "2022-01-15",
                description = "Кафе1"
            ),
            ExpenseInfoItem(
                expenseId = "cafe2",
                isPayed = false,
                totalAmount = 200,
                date = "2022-01-16",
                description = "Кафе2"
            ),
            ExpenseInfoItem(
                expenseId = "cafe3",
                isPayed = false,
                totalAmount = 100,
                date = "2022-01-17",
                description = "Кафе3"
            ),
        )
        assertEquals(factResult, actualResult)
    }

    @Test
    fun updateDebtorTable_DebtorPaidRequest() = launchTest {
        val mockedDebtor = ExpenseDebtor("id", "some_expense", 100, "some_id", false)

        db.debtorDAO.insertAll(mockedDebtor)
        db.debtorDAO.updateDebtor(DebtorPaidRequest(mockedDebtor.id, true))

        val factResult = db.debtorDAO.getExpenseDebtor(mockedDebtor.id)
        val actualResult = mockedDebtor.copy(isDebtPayed = true)

        assertEquals(factResult, actualResult)
    }

    @Test
    fun tripTotalAmountTest() = launchTest {
        val mockedExpenseDebtors = listOf<ExpenseDebtor>(
            ExpenseDebtor(
                id = "1",
                expenseId = "1",
                debtAmount = 100,
                participantId = Const.SELF_ID,
                isDebtPayed = true
            ),
            ExpenseDebtor(
                id = "2",
                expenseId = "1",
                debtAmount = 100,
                participantId = Const.SELF_ID,
                isDebtPayed = false
            ),
            ExpenseDebtor(
                id = "3",
                expenseId = "1",
                debtAmount = 100,
                participantId = "other",
                isDebtPayed = false
            ),
        )
        val mockedExpense = Expense(
            id = "1",
            ownerId = "",
            description = "",
            tripId = "trip",
            amount = 500,
            date = ""
        )

        // action
        db.expenseDAO.insetAll(mockedExpense)
        db.debtorDAO.insertAll(*mockedExpenseDebtors.toTypedArray())

        val factResult = db.tripDAO.getTripCostAmount(tripId = "trip")
        val actualResult = 200

        assertEquals(factResult, actualResult)
    }

    @Test
    fun selfIdInAllTrips() = launchTest {
        val actualResult = listOf(
            Participant(id = Const.SELF_ID, name = Const.SELF_ID, tripId = Const.ALL_TRIPS)
        )
        val factResult = db.participantDAO.getParticipants("any")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun removeExpenseTest() = launchTest {
        db.expenseDAO.insetAll(
            Expense(
                id = "1",
                ownerId = "kto-to",
                description = "Hello",
                tripId = "Magic",
                amount = 0,
                date = ""
            )
        )
        db.expenseDAO.deleteExpense("1")
        val factExpenses = db.expenseDAO.getExpenses("Magic")
        assert(factExpenses.isEmpty())
    }
}