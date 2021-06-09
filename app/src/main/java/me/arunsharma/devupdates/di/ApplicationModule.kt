package me.arunsharma.devupdates.di

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arunsharma.devupdates.prefs.AppPrefs
import me.arunsharma.devupdates.prefs.BasePrefs
import me.arunsharma.devupdates.utils.EventBus
import me.arunsharma.devupdates.utils.EventBusImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun provideAppPrefs(prefs: AppPrefs): BasePrefs

    @Binds
    @Singleton
    abstract fun provideEventBus(eventBus: EventBusImpl): EventBus

    companion object {
        @Provides
        fun providesMoshi() = Moshi.Builder().build()
    }
}