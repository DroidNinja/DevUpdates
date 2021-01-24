package com.dev.network.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val TIME_OUT: Long = 30

@Module
class NetworkModule {

    @Provides
    fun provideFCOKHttpAuth(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    }
}