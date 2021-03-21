package com.dev.rss

import com.dev.rss.model.blogspot.Feed
import com.dev.rss.model.rss.ChannelFeed
import com.dev.rss.model.rss.RSSFeed
import retrofit2.http.GET
import retrofit2.http.Url


interface ServiceRSS {

    @GET
    suspend fun getFeed(@Url url: String): Feed

    @GET
    suspend fun getRSSFeed(@Url url: String): RSSFeed

    @GET
    suspend fun getRSSChannelFeed(@Url url: String): ChannelFeed

    companion object {
        const val ENDPOINT = "https://medium.com/"
        const val SERVICE_KEY_BLOGSPOT = "BLOGSPOT"
        const val SERVICE_KEY_RSS= "RSS"
        const val SERVICE_KEY_RSS_CHANNEL= "RSS_CHANNEL"
    }
}