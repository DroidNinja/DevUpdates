package com.devupdates.medium.models

data class StreamItem(
    val createdAt: Long,
    val itemType: String,
    val postPreview: PostPreview,
    val randomId: String,
    val type: String
)

data class PostPreview(
    val postId: String
)