package com.dev.hackernews

import com.dev.hackernews.models.HackerNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceHackerNews {

    @GET("search")
    suspend fun getFrontPage(
        @Query("tags") tags: String,
        @Query("hitsPerPage") hitsPerPage: Int
    ): HackerNewsResponse

    companion object {
        const val ENDPOINT = "https://hn.algolia.com/api/v1/"
        const val SERVICE_KEY = "HACKER_NEWS"
        const val ITEM_URL = "https://news.ycombinator.com/item?id="
    }
}
