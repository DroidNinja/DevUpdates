package me.arunsharma.devupdates.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arunsharma.devupdates.prefs.AppPrefs
import me.arunsharma.devupdates.prefs.BasePrefs
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun provideAppPrefs(prefs: AppPrefs): BasePrefs
}