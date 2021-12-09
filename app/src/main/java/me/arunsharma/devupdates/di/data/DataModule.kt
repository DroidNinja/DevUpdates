package me.arunsharma.devupdates.di.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.arunsharma.devupdates.data.AppDatabase
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.SourceConfigStoreImpl
import me.arunsharma.devupdates.utils.cache.CachingProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideSourceConfigStore(
        @ApplicationContext context: Context,
        cachingProvider: CachingProvider,
        moshi: Moshi,
        serviceConfig: ServiceConfig,
    ): SourceConfigStore {
        return SourceConfigStoreImpl(context, cachingProvider, moshi, serviceConfig)
    }

    @Singleton
    @Provides
    fun provideCachingProvider(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): CachingProvider {
        return CachingProvider(context, moshi)
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

    @Singleton
    @Provides
    fun provideConfigRepo(): ServiceConfig {
        return Retrofit.Builder().baseUrl(ServiceConfig.ENDPOINT)
            .client(
                OkHttpClient.Builder().build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ServiceConfig::class.java)
    }
}