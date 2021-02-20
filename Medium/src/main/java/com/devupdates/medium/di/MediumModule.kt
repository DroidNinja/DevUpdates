package com.devupdates.medium.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.network.di.NetworkModule
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import com.devupdates.medium.APIMedium
import com.devupdates.medium.ServiceMedium
import com.devupdates.medium.ServiceMediumRequest
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [NetworkModule::class])
abstract class MediumModule {

    @IntoMap
    @ServiceKey(ServiceMedium.SERVICE_KEY)
    @Binds
    internal abstract fun mediumAPI(mediumService: APIMedium): ServiceIntegration

    companion object {
        @Provides
        internal fun provideMediumService(okhttpBuilder: OkHttpClient.Builder): ServiceMedium {
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
                        .addInterceptor(HttpLoggingInterceptor())
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceMedium::class.java)
        }
    }
}