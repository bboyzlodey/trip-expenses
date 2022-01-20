package com.skarlat.tripexpenses.di

import android.content.Context
import androidx.room.Room
import com.skarlat.tripexpenses.data.local.database.*
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
        return Room.databaseBuilder(context, AppDatabase::class.java, "expenses.db").build()
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