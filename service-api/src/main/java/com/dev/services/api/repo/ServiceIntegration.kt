package com.dev.services.api.repo

import com.dev.network.model.ResponseStatus
import com.dev.services.api.models.ServiceRequest
import com.dev.services.api.models.ServiceResult

interface ServiceIntegration {
    suspend fun  getData(request: ServiceRequest) : ResponseStatus<ServiceResult>
}