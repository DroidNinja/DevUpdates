package com.devupdates.github.models


data class GithubTrendingResponseItem(
    val author: String?,
    val avatar: String? = null,
    val builtBy: List<BuiltBy>? = null,
    val currentPeriodStars: Int?,
    val description: String?,
    val forks: Int?,
    val language: String?,
    val languageColor: String?,
    val name: String,
    val stars: Int?,
    val url: String
)

data class BuiltBy(
    val avatar: String?,
    val href: String?,
    val username: String?
)