package com.dev.hackernews

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.api.models.ServiceItem
import com.dev.services.api.models.ServiceRequest
import com.dev.services.api.models.ServiceResult
import com.dev.services.api.repo.ServiceIntegration
import java.net.URI
import javax.inject.Inject

class APIHackerNews @Inject constructor(val service: ServiceHackerNews) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        try {
            val result = service.getFrontPage(tags = "front_page", hitsPerPage = 30).hits.map { item ->
                val createdAt = (item.createdAtSeconds ?: 0L) * 1000L
                ServiceItem(
                    title = item.title ?: "",
                    description = item.url?.let { getHost(it) },
                    author = item.author,
                    topTitleText = item.author + " ● " + DateUtils.getRelativeTimeSpanString(createdAt),
                    likes = "▲ " + (item.points ?: 0),
                    actionUrl = item.url ?: (ServiceHackerNews.ITEM_URL + item.objectId),
                    sourceType = request.type.toString(),
                    createdAt = createdAt,
                    groupId = request.name
                )
            }
            return ResponseStatus.success(ServiceResult(result))
        } catch (exception: Exception) {
            return ResponseStatus.failure(APIErrorException.newInstance(exception))
        }
    }

    private fun getHost(url: String): String? = runCatching { URI(url).host }.getOrNull()
}
