package me.arunsharma.devupdates.di.activity

import dagger.Module
import dagger.Provides
import me.arunsharma.devupdates.navigator.MainNavigator
import me.arunsharma.devupdates.navigator.MainNavigatorImpl

@Module
class MainModule {

    @Provides
    fun provideNavigator(): MainNavigator = MainNavigatorImpl()
}