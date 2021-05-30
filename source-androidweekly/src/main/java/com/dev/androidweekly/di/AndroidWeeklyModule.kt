package com.dev.androidweekly.di

import com.dev.androidweekly.APIAndroidWeekly
import com.dev.androidweekly.AndroidWeeklyParser
import com.dev.androidweekly.ServiceAndroidWeekly
import com.dev.core.di.annotations.ServiceKey
import com.dev.network.di.NetworkModule
import com.dev.network.utils.DecodingConverter
import com.dev.services.repo.ServiceIntegration
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class AndroidWeeklyModule {

    @IntoMap
    @ServiceKey(ServiceAndroidWeekly.SERVICE_KEY)
    @Binds
    internal abstract fun androidWeekklyAPI(androidWeekkly: APIAndroidWeekly): ServiceIntegration

    companion object {
        @Provides
        internal fun provideGitHubService(okhttpBuilder: OkHttpClient.Builder): ServiceAndroidWeekly {
            return Retrofit.Builder().baseUrl(ServiceAndroidWeekly.ENDPOINT)
                .client(okhttpBuilder
                    .build())
                .addConverterFactory(DecodingConverter.newFactory(AndroidWeeklyParser::parse))
                .build()
                .create(ServiceAndroidWeekly::class.java)
        }
    }
}