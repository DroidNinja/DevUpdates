package com.dev.hackernews.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HackerNewsResponse(
    @Json(name = "hits")
    val hits: List<HackerNewsStory>
)

@JsonClass(generateAdapter = true)
data class HackerNewsStory(
    @Json(name = "objectID")
    val objectId: String,
    val title: String?,
    val url: String?,
    val author: String?,
    val points: Int?,
    @Json(name = "num_comments")
    val numComments: Int?,
    @Json(name = "created_at_i")
    val createdAtSeconds: Long?
)
