package com.devupdates.medium

import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import com.devupdates.medium.models.MediumRequest
import javax.inject.Inject

class APIMedium @Inject constructor(val service: ServiceMedium) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): List<ServiceItem> {
        var username = ""
        if (request is ServiceMediumRequest) {
            username = request.username
        }
        val mediumRequest = MediumRequest(25, System.currentTimeMillis())
        val response = service.getFeed(username, mediumRequest)
        return if (response.isSuccessful) {
            response.body()?.payload?.values?.map { item ->
                ServiceItem(
                    title = item.title,
                    description = item.getDescription(),
                    author = response.body()?.payload?.getAuthorName(item.creatorId),
                    likes = "♥︎ " + item.virtuals.totalClapCount,
                    actionUrl = ServiceMedium.ENDPOINT + username + "/" + item.uniqueSlug
                )
            } ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }
}