package com.dev.services.models

data class ServiceResult(
    var data: List<ServiceItem>,
    var page: String? = null
)
