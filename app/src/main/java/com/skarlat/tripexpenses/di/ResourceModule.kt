package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.utils.StringResourceWrapper
import com.skarlat.tripexpenses.utils.StringResourceWrapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ResourceModule {

    @Binds
    fun bindStringResourceWrapper(impl: StringResourceWrapperImpl): StringResourceWrapper

}