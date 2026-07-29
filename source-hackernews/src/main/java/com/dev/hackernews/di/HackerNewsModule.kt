package com.dev.hackernews.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.hackernews.APIHackerNews
import com.dev.hackernews.ServiceHackerNews
import com.dev.network.di.NetworkModule
import com.dev.services.api.repo.ServiceIntegration
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class HackerNewsModule {

    @IntoMap
    @ServiceKey(ServiceHackerNews.SERVICE_KEY)
    @Binds
    internal abstract fun hackerNewsAPI(hackerNewsService: APIHackerNews): ServiceIntegration

    companion object {
        @Provides
        internal fun provideHackerNewsService(
            okhttpBuilder: OkHttpClient.Builder,
            moshi: Moshi
        ): ServiceHackerNews {
            return Retrofit.Builder().baseUrl(ServiceHackerNews.ENDPOINT)
                .client(okhttpBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ServiceHackerNews::class.java)
        }
    }
}
