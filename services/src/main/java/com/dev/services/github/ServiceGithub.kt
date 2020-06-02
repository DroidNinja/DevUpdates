package com.dev.services.github

import com.dev.services.github.models.GithubTrendingResponseItem

interface ServiceGithub {
    suspend fun getTrending(): ArrayList<GithubTrendingResponseItem>
}