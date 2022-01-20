package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.business.calculator.DebtCalculator
import com.skarlat.tripexpenses.business.calculator.IDebtorCalculator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface GlobalUtilsModule {

    @Binds
    fun bindDebtCalculator(util: DebtCalculator): IDebtorCalculator

}