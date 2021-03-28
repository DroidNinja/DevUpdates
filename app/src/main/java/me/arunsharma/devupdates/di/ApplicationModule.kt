package me.arunsharma.devupdates.di

import android.app.Application
import android.content.Context
import com.dev.core.di.annotations.ApplicationContext
import dagger.Binds
import dagger.Module
import me.arunsharma.devupdates.prefs.AppPrefs
import me.arunsharma.devupdates.prefs.BasePrefs
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Binds
    @ApplicationContext
    abstract fun provideApplicationContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun provideAppPrefs(prefs: AppPrefs): BasePrefs
}