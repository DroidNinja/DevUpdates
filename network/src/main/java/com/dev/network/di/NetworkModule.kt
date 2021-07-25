package com.dev.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val TIME_OUT: Long = 30

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideFCOKHttpAuth(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        if (interceptors.isNotEmpty()) {
            interceptors.forEach {
                builder.addInterceptor(it)
            }
        }
        return builder
    }
}