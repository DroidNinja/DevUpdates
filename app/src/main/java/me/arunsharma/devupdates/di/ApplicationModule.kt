package me.arunsharma.devupdates.di

import androidx.work.Configuration
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import me.arunsharma.devupdates.prefs.AppPrefs
import me.arunsharma.devupdates.prefs.BasePrefs
import me.arunsharma.devupdates.utils.EventBus
import me.arunsharma.devupdates.utils.EventBusImpl
import me.arunsharma.devupdates.workers.DevUpdatesWorkerFactory
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModule {

    @Multibinds
    internal abstract fun provideInterceptors(): Set<Interceptor>

    @Binds
    @Singleton
    abstract fun provideAppPrefs(prefs: AppPrefs): BasePrefs

    @Binds
    @Singleton
    abstract fun provideEventBus(eventBus: EventBusImpl): EventBus

    companion object {
        @Provides
        fun providesMoshi() = Moshi.Builder().build()

        @Singleton
        @Provides
        fun provideWorkManagerConfiguration(
            devUpdatesWorkerFactory: DevUpdatesWorkerFactory
        ): Configuration {
            return Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .setWorkerFactory(devUpdatesWorkerFactory)
                .build()
        }
    }
}