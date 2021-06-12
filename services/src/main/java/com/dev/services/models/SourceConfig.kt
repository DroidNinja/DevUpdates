package com.dev.services.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceConfig(
    @Json(name = "data")
    val data: List<ServiceRequest>
)