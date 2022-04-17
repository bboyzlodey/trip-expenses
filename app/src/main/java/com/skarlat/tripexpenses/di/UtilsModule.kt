package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.business.calculation.DebtCalculator
import com.skarlat.tripexpenses.business.calculation.IDebtorCalculator
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