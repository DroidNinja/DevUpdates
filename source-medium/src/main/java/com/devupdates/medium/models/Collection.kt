package com.devupdates.medium.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection(
    @Json(name = "firstPublishedAt")
    val createdAt: Long,
    val creatorId: String,
    val title: String,
    val type: String,
    val uniqueSlug: String,
    val updatedAt: Long,
    @Json(name = "virtuals")
    var virtuals: Virtuals
) {
    fun getDescription(): String {
        return virtuals.subtitle
    }
}