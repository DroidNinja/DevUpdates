package com.dev.androidweekly

import com.dev.androidweekly.models.AndroidWeeklyFeedItem
import retrofit2.http.GET
import retrofit2.http.Url

interface ServiceAndroidWeekly {

    @GET
    suspend fun getFeed(
        @Url url: String
    ): List<AndroidWeeklyFeedItem>

    companion object {
        const val ENDPOINT = "https://androidweekly.net/"
        const val SERVICE_KEY = "ANDROID_WEEKLY"
    }
}