package com.dev.services.api.models

import com.dev.services.api.models.ServiceRequest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceConfig(
    @Json(name = "data")
    val data: List<ServiceRequest>
)