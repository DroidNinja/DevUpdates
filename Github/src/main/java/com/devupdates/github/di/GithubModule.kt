package com.devupdates.github.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.network.di.NetworkModule
import com.dev.network.utils.DecodingConverter
import com.dev.services.models.ServiceRequest
import com.devupdates.github.APIGithub
import com.devupdates.github.GitHubTrendingParser
import com.devupdates.github.ServiceGithub
import com.dev.services.repo.ServiceIntegration
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
abstract class GithubModule {

    @IntoMap
    @ServiceKey(ServiceGithub.SERVICE_KEY)
    @Binds
    internal abstract fun githubAPI(githubService: APIGithub): ServiceIntegration

    companion object {
        @Provides
        internal fun provideGitHubService(okhttpBuilder: OkHttpClient.Builder): ServiceGithub {
            return Retrofit.Builder().baseUrl(ServiceGithub.ENDPOINT)
                .client(okhttpBuilder
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                    .build())
                .addConverterFactory(DecodingConverter.newFactory(GitHubTrendingParser::parse))
                .build()
                .create(ServiceGithub::class.java)
        }
    }
}