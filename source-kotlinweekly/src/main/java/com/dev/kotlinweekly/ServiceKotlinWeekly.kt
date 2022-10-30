package com.dev.kotlinweekly

import com.dev.kotlinweekly.models.KotlinWeeklyFeedItem
import retrofit2.http.GET
import retrofit2.http.Url

interface ServiceKotlinWeekly {

    @GET
    suspend fun getFeed(
        @Url url: String
    ): List<KotlinWeeklyFeedItem>

    companion object {
        const val ENDPOINT = "https://kotlin-weekly.web.app/"
        const val SERVICE_KEY = "KOTLIN_WEEKLY"
    }
}