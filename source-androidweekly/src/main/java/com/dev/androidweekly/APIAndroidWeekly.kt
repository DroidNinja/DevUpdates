package com.dev.androidweekly

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URL
import javax.inject.Inject

class APIAndroidWeekly @Inject constructor(val service: ServiceAndroidWeekly) : ServiceIntegration {

    private var latestIssue: Int = 0

    override suspend fun getData(request: ServiceRequest): ResponseStatus<List<ServiceItem>> {
        try {
            if (latestIssue == 0) {
                latestIssue = getLatestIssue().toInt()
            }
            if (isPagination(request)) {
                latestIssue--
            }

            val result =
                service.getFeed("https://androidweekly.net/issues/issue-$latestIssue").map { item ->
                    ServiceItem(
                        title = item.title,
                        description = item.description,
                        author = item.issue + " ● " + DateUtils.getRelativeTimeSpanString(item.createdAt ?: 0),
                        likes = item.baseLink,
                        actionUrl = item.link,
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

    fun isPagination(request: ServiceRequest): Boolean {
        return request.next > 0
    }

    private suspend fun getLatestIssue(): String {
        return withContext(Dispatchers.IO) {
            val url = "https://androidweekly.net/archive"
            val doc = Jsoup.parse(URL(url), 30000)

            val issueText =
                doc.select("body > section > div > div > ul > li:nth-child(1) > h3 > a").text()
            issueText.substring(issueText.lastIndexOf("#") + 1, issueText.length)
        }
    }
}