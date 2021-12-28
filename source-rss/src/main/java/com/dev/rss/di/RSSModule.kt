package com.dev.rss.di

import com.dev.core.di.annotations.ServiceKey
import com.dev.network.di.NetworkModule
import com.dev.rss.APIBlogspotRSS
import com.dev.rss.APIRSS
import com.dev.rss.APIRSSChannel
import com.dev.rss.ServiceRSS
import com.dev.services.repo.ServiceIntegration
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class RSSModule {

    @IntoMap
    @ServiceKey(ServiceRSS.SERVICE_KEY_BLOGSPOT)
    @Binds
    internal abstract fun apiBlogspotRSS(apiBlogspotRSS: APIBlogspotRSS): ServiceIntegration

    @IntoMap
    @ServiceKey(ServiceRSS.SERVICE_KEY_RSS)
    @Binds
    internal abstract fun apirss(apirss: APIRSS): ServiceIntegration

    @IntoMap
    @ServiceKey(ServiceRSS.SERVICE_KEY_RSS_CHANNEL)
    @Binds
    internal abstract fun apirssChannel(apirssChannel: APIRSSChannel): ServiceIntegration


    companion object {
        @Provides
        internal fun provideRSSParser(okhttpBuilder: OkHttpClient.Builder): ServiceRSS {
            return Retrofit.Builder().baseUrl(ServiceRSS.ENDPOINT)
                .client(okhttpBuilder
                    .addInterceptor { chain ->
                        val response = chain.proceed(chain.request())
                        val responseText = response.body?.string()?.replace("(<\\?xml.*?\\?>)","")
                        val body = responseText?.toResponseBody(response.body?.contentType())
                        response.newBuilder().body(body).build()
                    }
                    .build())

                .addConverterFactory(TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .addTypeConverter(String::class.java,  HtmlEscapeStringConverter())
                    .exceptionOnUnreadXml(false)
                    .build()))
                .build()
                .create(ServiceRSS::class.java)
        }
    }
}