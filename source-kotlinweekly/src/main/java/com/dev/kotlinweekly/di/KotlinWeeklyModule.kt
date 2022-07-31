package com.dev.kotlinweekly.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.kotlinweekly.APIKotlinWeekly
import com.dev.kotlinweekly.KotlinWeeklyParser
import com.dev.kotlinweekly.ServiceKotlinWeekly
import com.dev.network.di.NetworkModule
import com.dev.network.utils.DecodingConverter
import com.dev.services.repo.ServiceIntegration
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class KotlinWeeklyModule {

    @IntoMap
    @ServiceKey(ServiceKotlinWeekly.SERVICE_KEY)
    @Binds
    internal abstract fun apiKotlinWeeklyAPI(apiKotlinWeekly: APIKotlinWeekly): ServiceIntegration

    companion object {
        @Provides
        internal fun provideGitHubService(okhttpBuilder: OkHttpClient.Builder): ServiceKotlinWeekly {
            return Retrofit.Builder().baseUrl(ServiceKotlinWeekly.ENDPOINT)
                .client(okhttpBuilder
                    .build())
                .addConverterFactory(DecodingConverter.newFactory(KotlinWeeklyParser::parse))
                .build()
                .create(ServiceKotlinWeekly::class.java)
        }
    }
}