package com.dev.services.api.models

import com.dev.services.api.models.ServiceItem

data class ServiceResult(
    var data: List<ServiceItem>,
    var page: String? = null
)
