package com.dev.services.twitter

import com.dev.services.models.ServiceItem
import com.dev.services.repo.ServiceIntegration
import javax.inject.Inject

class APITwitter  @Inject constructor(github: ServiceTwitter): ServiceIntegration {


    override suspend fun getData(): List<ServiceItem> {
        return mutableListOf()
    }
}