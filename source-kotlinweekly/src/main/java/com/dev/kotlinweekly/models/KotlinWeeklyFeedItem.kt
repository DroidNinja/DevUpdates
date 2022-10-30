package com.dev.kotlinweekly.models

data class KotlinWeeklyFeedItem(
    val title: String,
    val description: String,
    val link: String,
    val issue: String,
    val baseLink: String,
    val createdAt: Long
)