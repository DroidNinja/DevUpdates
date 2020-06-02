package me.arunsharma.devupdates.di

import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
class DebugApplicationModule {

    @Provides
    fun provideDebugTree(): Timber.Tree = Timber.DebugTree()
}