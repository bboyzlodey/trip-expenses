package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.business.calculation.input.ExpressionReader
import com.skarlat.tripexpenses.business.calculation.input.ExpressionReaderImpl
import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculator
import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DistributionModule {

    @Binds
    fun bindCalculator(runTimeInputCalculatorImpl: RealTimeInputCalculatorImpl): RealTimeInputCalculator

    @Binds
    fun bindExprReader(expressionReaderImpl: ExpressionReaderImpl): ExpressionReader

}