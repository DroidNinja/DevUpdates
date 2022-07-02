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
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.math.min

class APIRSSChannel @Inject constructor(val serviceRSS: ServiceRSS) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        if (request.metadata != null) {
            val data = serviceRSS.getRSSChannelFeed(request.metadata!!["url"] ?: "")
            val result = data.channel.item.map { item ->
                val htmlText = Jsoup.parse(item.summary).text()
                val createdAt = DateTimeHelper.formatDate(
                    AppConstants.FORMAT_ISO_RFC_822,
                    item.pubDate
                )
                ServiceItem(
                    title = item.title ?: "",
                    description = htmlText?.substring(0, min(256, htmlText.length)) + "...",
                    author = item.author,
                    likes = DateUtils.getRelativeTimeSpanString(createdAt).toString(),
                    actionUrl = item.link ?: "",
                    sourceType = DataSource.RSS_CHANNEL.toString(),
                    groupId = request.name,
                    createdAt = createdAt,
                    topTitleText = item.author
                )
            }
            return ResponseStatus.success(ServiceResult(result))
        } else {
            return ResponseStatus.failure(APIErrorException(APIError("BP", "Url not specified")))
        }
    }
}