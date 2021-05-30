package me.arunsharma.devupdates.di.activity

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.arunsharma.devupdates.navigator.MainNavigator
import me.arunsharma.devupdates.navigator.MainNavigatorImpl

@InstallIn(ActivityComponent::class)
@Module
class MainModule {

    @Provides
    fun provideNavigator(): MainNavigator = MainNavigatorImpl()
}