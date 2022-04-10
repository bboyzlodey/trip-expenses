package com.skarlat.tripexpenses.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skarlat.tripexpenses.data.local.entity.Expense
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor
import com.skarlat.tripexpenses.data.local.entity.Participant
import com.skarlat.tripexpenses.data.local.entity.Trip

@Database(
    entities = [Expense::class, ExpenseDebtor::class, Participant::class, Trip::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract val debtorDAO: DebtorDAO
    abstract val expenseDAO: ExpenseDAO
    abstract val participantDAO: ParticipantDAO
    abstract val tripDAO: TripDAO
}