package com.dev.network.factory

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {
    private const val TIME_OUT: Long = 1 // 1 min

    fun provideFCOKHttpAuth(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MINUTES)
                .writeTimeout(TIME_OUT, TimeUnit.MINUTES)
                .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
    }

    fun provideRetrofit(baseURL: String, okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().serializeNulls().create()
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }
}