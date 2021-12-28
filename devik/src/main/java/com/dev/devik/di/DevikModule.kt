package com.dev.devik.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
class DevikModule {

    @Provides
    @IntoSet
    fun provideStethoInterceptor(): Interceptor {
        return StethoInterceptor()
    }

    @Provides
    @IntoSet
    fun provideChuckerInterceptor(@ApplicationContext context: Context): Interceptor {
        return ChuckerInterceptor(context)
    }

//    @Provides
//    @IntoSet
//    fun provideHttpLoggingInterceptor(): Interceptor {
//        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    }
}