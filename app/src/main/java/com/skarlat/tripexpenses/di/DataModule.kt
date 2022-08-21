package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindTripRepository(repository: TripRepository): ITripRepository

    @Binds
    fun bindExpenseRepository(repository: ExpenseRepository): IExpenseRepository

    @Binds
    fun bindParticipantRepository(repository: ParticipantRepository): IParticipantRepository

    @Binds
    fun bindDebtorRepository(repository: DebtorRepository): IDebtorRepository

    @Binds
    fun bindNameDictionary(nameDictionaryImpl: NameDictionaryImpl): NameDictionary

}