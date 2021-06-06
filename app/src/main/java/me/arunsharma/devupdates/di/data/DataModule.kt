package me.arunsharma.devupdates.di.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.arunsharma.devupdates.data.AppDatabase
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.SourceConfigStoreImpl
import me.arunsharma.devupdates.utils.cache.CachingProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideSourceConfigStore(
        @ApplicationContext context: Context,
        cachingProvider: CachingProvider
    ): SourceConfigStore {
        return SourceConfigStoreImpl(context, cachingProvider)
    }

    @Singleton
    @Provides
    fun provideCachingProvider(
        @ApplicationContext context: Context,
    ): CachingProvider {
        return CachingProvider(context)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}