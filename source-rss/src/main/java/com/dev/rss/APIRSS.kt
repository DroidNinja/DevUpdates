package com.dev.rss

import android.text.format.DateUtils
import com.dev.core.AppConstants
import com.dev.core.utils.DateTimeHelper
import com.dev.network.model.APIError
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.models.ServiceResult
import com.dev.services.repo.ServiceIntegration
import javax.inject.Inject
import kotlin.math.min

class APIRSS @Inject constructor(val serviceRSS: ServiceRSS) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        if (request.metadata != null) {
            val data = serviceRSS.getRSSFeed(request.metadata!!["url"] ?: "")
            val result = data.entry.map { item ->
                val createdAt = DateTimeHelper.formatDate(
                    AppConstants.FORMAT_ISO_OFFSET_DATE_TIME,
                    item.updated
                )
                ServiceItem(
                    title = item.title ?: "",
                    description = item.summary?.substring(0, min(256, item.summary.length)) + "...",
                    author = item.author?.name,
                    likes = DateUtils.getRelativeTimeSpanString(createdAt).toString(),
                    actionUrl = item.link?.href ?: "",
                    sourceType = DataSource.RSS.toString(),
                    groupId = request.name,
                    createdAt = createdAt,
                    topTitleText = item.author?.name
                )
            }
            return ResponseStatus.success(ServiceResult(result))
        } else {
            return ResponseStatus.failure(APIErrorException(APIError("BP", "Url not specified")))
        }
    }
}