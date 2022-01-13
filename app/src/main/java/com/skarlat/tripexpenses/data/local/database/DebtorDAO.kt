package com.skarlat.tripexpenses.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import com.skarlat.tripexpenses.data.local.entity.ExpenseDebtor

@Dao
interface DebtorDAO {

    @Insert
    suspend fun insert(entity: ExpenseDebtor)

}