package com.devupdates.medium.models

data class MediumRequest(
    var limit: Int = 10,
    var to: Long = System.currentTimeMillis()
)