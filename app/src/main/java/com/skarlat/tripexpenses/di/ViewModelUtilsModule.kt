package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.business.calculation.ICostCalculator
import com.skarlat.tripexpenses.business.calculation.TotalCostCalculator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface ViewModelUtilsModule {

    @Binds
    fun bindTotalCostCalculator(util: TotalCostCalculator): ICostCalculator

}