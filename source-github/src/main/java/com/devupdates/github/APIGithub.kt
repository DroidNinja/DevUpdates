package com.devupdates.github

import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.models.ServiceResult
import com.dev.services.repo.ServiceIntegration
import timber.log.Timber
import javax.inject.Inject

class APIGithub @Inject constructor(val service: ServiceGithub) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        try {
            Timber.d("Debug--getData")
            val result = service.getTrending(request.metadata?.get(ServiceGithub.SELECTED_LANGUAGE) ?: "", "daily").map { item ->
                ServiceItem(
                    title = item.author + " / " + item.name,
                    description = item.description,
                    author = item.author,
                    topTitleText = item.language + " ● " + item.currentPeriodStars + " stars today",
                    likes = "★ " + item.stars?.toString(),
                    actionUrl = item.url,
                    sourceType = request.type.toString(),
                    createdAt = System.currentTimeMillis(),
                    groupId = request.name
                )
            }
            return ResponseStatus.success(ServiceResult(result))
        } catch (exception: Exception) {
            Timber.d("Debug--"+ exception.message)
            return ResponseStatus.failure(APIErrorException.newInstance(exception))
        }
    }
}