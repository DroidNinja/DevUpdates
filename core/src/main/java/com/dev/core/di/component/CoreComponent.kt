package com.dev.core.di.component

import android.content.Context
import com.dev.core.di.module.CoreModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {
    fun context(): Context
}