package com.dev.androidweekly.models

data class AndroidWeeklyFeedItem(
    val title: String,
    val description: String,
    val link: String,
    val issue: String,
    val baseLink: String,
    val createdAt: Long
)