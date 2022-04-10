package com.skarlat.tripexpenses.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skarlat.tripexpenses.data.local.database.*
import com.skarlat.tripexpenses.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "expenses.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL("INSERT OR IGNORE INTO participant VALUES('${Const.SELF_ID}', '${Const.SELF_ID}', '${Const.ALL_TRIPS}')")
                }
            })
            .build()
    }

    @Provides
    fun provideTripDAO(database: AppDatabase): TripDAO = database.tripDAO

    @Provides
    fun provideExpensesDAO(database: AppDatabase): ExpenseDAO = database.expenseDAO

    @Provides
    fun provideParticipantDAO(database: AppDatabase): ParticipantDAO = database.participantDAO

    @Provides
    fun provideDebtorDAO(database: AppDatabase): DebtorDAO = database.debtorDAO
}