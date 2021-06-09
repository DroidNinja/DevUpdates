package com.devupdates.medium.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediumResponse(

    val payload: PayLoad,
    val success: Boolean)
