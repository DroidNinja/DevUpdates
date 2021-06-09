package com.devupdates.medium.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class References(
    @Json(name = "User")
    val user: Map<String, User>,
    @Json(name = "Post")
    var post: Map<String, Collection>?
)