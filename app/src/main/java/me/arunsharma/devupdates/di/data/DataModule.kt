package me.arunsharma.devupdates.di.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dev.core.di.annotations.ApplicationContext
import dagger.Module
import dagger.Provides
import me.arunsharma.devupdates.data.AppDatabase
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.SourceConfigStoreImpl
import me.arunsharma.devupdates.utils.cache.CachingProvider

@Module
class DataModule {

    @Provides
    fun provideSourceConfigStore(
        @ApplicationContext context: Context,
        cachingProvider: CachingProvider
    ): SourceConfigStore {
        return SourceConfigStoreImpl(context, cachingProvider)
    }

    @Provides
    fun provideCachingProvider(
        @ApplicationContext context: Context,
    ): CachingProvider {
        return CachingProvider(context)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}