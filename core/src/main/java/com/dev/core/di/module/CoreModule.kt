package com.dev.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}