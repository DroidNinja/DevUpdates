package com.devupdates.medium.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Virtuals(
    val subtitle: String,
    val totalClapCount: Int)