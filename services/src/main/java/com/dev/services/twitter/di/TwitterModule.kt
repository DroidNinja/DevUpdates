package com.dev.services.twitter.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.services.repo.ServiceIntegration
import com.dev.services.twitter.APITwitter
import com.dev.services.twitter.ServiceTwitter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class TwitterModule {

    @IntoMap
    @ServiceKey(ServiceTwitter.SERVICE_KEY)
    @Binds
    internal abstract fun twitterAPI(apiTwitter: APITwitter): ServiceIntegration

    companion object {
        @Provides
        internal fun provideGitHubService(): ServiceTwitter {
            return Retrofit.Builder().baseUrl(ServiceTwitter.ENDPOINT)
                .build()
                .create(ServiceTwitter::class.java)
        }
    }
}