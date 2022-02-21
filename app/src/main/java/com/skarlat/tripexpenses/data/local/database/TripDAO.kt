package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skarlat.tripexpenses.data.local.entity.Trip
import com.skarlat.tripexpenses.utils.Const
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDAO {

    @Query("SELECT * FROM Trip")
    suspend fun getTrips(): List<Trip>

    @Query("SELECT * FROM Trip WHERE id =:tripId")
    suspend fun getTrip(tripId: String): Trip

    @Query("SELECT * FROM Trip")
    fun getTripsFlow(): Flow<List<Trip>>

    @Insert
    suspend fun insertTrip(trip: Trip)

    @Query("SELECT sum(ExpenseDebtor.debt_amount) FROM Expense, ExpenseDebtor WHERE Expense.trip_id = :tripId AND Expense.expense_id = ExpenseDebtor.expense_id AND ExpenseDebtor.participant_id = :participantId")
    suspend fun getTripCostAmount(tripId: String, participantId: String = Const.SELF_ID): Int?
}