package com.dev.core.di.scope

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@InstallIn(SingletonComponent::class)
@Module
class ScopeModule {
    @Provides
    fun provideCoroutineScope() = CoroutineScope(Job() + Dispatchers.Main)
}