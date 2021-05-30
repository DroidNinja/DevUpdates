package me.arunsharma.devupdates.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
class DebugApplicationModule {

    @Provides
    fun provideDebugTree(): Timber.Tree = Timber.DebugTree()
}