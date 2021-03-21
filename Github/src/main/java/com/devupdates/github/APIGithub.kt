package com.devupdates.github

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import javax.inject.Inject

class APIGithub @Inject constructor(val service: ServiceGithub) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<List<ServiceItem>> {
        try {
            var result = service.getTrending("kotlin", "daily").map { item ->
                ServiceItem(
                    title = item.author + " / " + item.name,
                    description = item.description,
                    author = item.language,
                    likes = "★ " + item.stars?.toString(),
                    actionUrl = item.url,
                    sourceType = request.type.toString(),
                    createdAt = System.currentTimeMillis(),
                    groupId = request.name
                )
            }
            return ResponseStatus.success(result)
        } catch (exception: Exception) {
            return ResponseStatus.failure(APIErrorException.newInstance(exception))
        }
    }
}