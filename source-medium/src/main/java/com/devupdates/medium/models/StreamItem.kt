package com.devupdates.medium.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreamItem(
    val createdAt: Long,
    val itemType: String,
    val postPreview: PostPreview,
    val randomId: String,
    val type: String
)

@JsonClass(generateAdapter = true)
data class PostPreview(
    val postId: String
)