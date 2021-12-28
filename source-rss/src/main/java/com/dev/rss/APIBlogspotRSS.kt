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

class APIBlogspotRSS @Inject constructor(val serviceRSS: ServiceRSS) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        if (request.metadata != null) {
            val url = request.metadata!!["url"] ?: ""
            var page = request.next?.toIntOrNull()
            if (page == null) {
                page = 1
            }

            //e.g.https://android-developers.googleblog.com/feeds/posts/default?start-index=1
            val data = serviceRSS.getFeed(url, page)
            val result = data.itemList?.map { item ->
                val htmlText = Jsoup.parse(item.content)
                val author = htmlText.select("p").first().text()
                val summary = htmlText.select("p").getOrNull(2)?.text()
                val createdAt = DateTimeHelper.formatDate(
                    AppConstants.FORMAT_ISO_OFFSET_DATE_TIME,
                    item.updated
                )
                ServiceItem(
                    title = item.title ?: "",
                    description = summary?.substring(0, min(256, summary.length)) + "...",
                    author = author,
                    likes = DateUtils.getRelativeTimeSpanString(createdAt).toString(),
                    actionUrl = item.getURL() ?: "",
                    sourceType = DataSource.BLOGSPOT.toString(),
                    groupId = request.name,
                    createdAt = createdAt,
                    topTitleText = author
                )
            } ?: mutableListOf()
            return ResponseStatus.success(ServiceResult(result, (result.size + page).toString()))
        } else {
            return ResponseStatus.failure(APIErrorException(APIError("BP", "Url not specified")))
        }
    }
}