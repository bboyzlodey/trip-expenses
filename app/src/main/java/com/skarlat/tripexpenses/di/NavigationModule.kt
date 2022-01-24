package com.skarlat.tripexpenses.di

import com.skarlat.tripexpenses.ui.navigation.Navigator
import com.skarlat.tripexpenses.ui.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindNavigator(navigator: NavigatorImpl): Navigator

}