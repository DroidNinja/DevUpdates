package com.devupdates.medium.models

import com.google.gson.annotations.SerializedName

data class Collection(
    val createdAt: Long,
    val creatorId: String,
    val title: String,
    val type: String,
    val uniqueSlug: String,
    val updatedAt: Long,
    @SerializedName("virtuals")
    var virtuals: Virtuals
) {
    fun getDescription(): String {
        return virtuals.subtitle
    }
}