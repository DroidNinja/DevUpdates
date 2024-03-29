package com.devupdates.medium.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.network.di.NetworkModule
import com.dev.services.api.repo.ServiceIntegration
import com.devupdates.medium.APIMedium
import com.devupdates.medium.ServiceMedium
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class MediumModule {

    @IntoMap
    @ServiceKey(ServiceMedium.SERVICE_KEY)
    @Binds
    internal abstract fun mediumAPI(mediumService: APIMedium): ServiceIntegration

    companion object {
        @Provides
        internal fun provideMediumService(
            okhttpBuilder: OkHttpClient.Builder,
            moshi: Moshi
        ): ServiceMedium {
            return Retrofit.Builder().baseUrl(ServiceMedium.ENDPOINT)
                .client(
                    okhttpBuilder
                        .addInterceptor { chain ->
                            val response = chain.proceed(chain.request())
                            val responseText = response.body?.string()
                            val body = responseText?.substring(responseText.indexOf("{"))
                                ?.toResponseBody(response.body?.contentType())
                            response.newBuilder().body(body).build()
                        }
                        .build()
                )
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ServiceMedium::class.java)
        }
    }
}