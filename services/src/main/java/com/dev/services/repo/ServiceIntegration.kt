package com.dev.services.repo

import com.dev.services.models.ServiceItem

interface ServiceIntegration {
    suspend fun getData() : List<ServiceItem>
}