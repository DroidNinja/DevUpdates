package com.dev.services.repo

import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest

interface ServiceIntegration {
    suspend fun  getData(request: ServiceRequest) : List<ServiceItem>
}