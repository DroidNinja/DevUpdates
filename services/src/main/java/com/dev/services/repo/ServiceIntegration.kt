package com.dev.services.repo

import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceRequest
import com.dev.services.models.ServiceResult

interface ServiceIntegration {
    suspend fun  getData(request: ServiceRequest) : ResponseStatus<ServiceResult>
}