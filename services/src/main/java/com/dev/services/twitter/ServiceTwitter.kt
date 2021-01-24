package com.dev.services.twitter

import com.dev.services.github.models.GithubTrendingResponseItem

interface ServiceTwitter {
    suspend fun getTrending(): ArrayList<GithubTrendingResponseItem>

    companion object {
        const val ENDPOINT = "https://github-trending-api.now.sh/"
        const val SERVICE_KEY = "twitter"
    }
}