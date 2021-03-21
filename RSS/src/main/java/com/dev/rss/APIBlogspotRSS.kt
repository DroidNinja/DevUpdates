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
import com.dev.services.repo.ServiceIntegration
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.math.min

class APIBlogspotRSS @Inject constructor(val serviceRSS: ServiceRSS) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<List<ServiceItem>> {
        if(request.metadata != null) {
            val data = serviceRSS.getFeed(request.metadata!!["url"] ?: "")
            return ResponseStatus.success(data.itemList.map { item ->

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
                    actionUrl = item.link ?: "",
                    sourceType = DataSource.BLOGSPOT.toString(),
                    groupId = request.name
                )
            })
        }
        else{
            return ResponseStatus.failure(APIErrorException(APIError("BP", "Url not specified")))
        }
    }
}