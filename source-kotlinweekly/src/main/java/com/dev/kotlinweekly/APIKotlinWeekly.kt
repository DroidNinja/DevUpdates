package com.dev.kotlinweekly

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.api.models.ServiceItem
import com.dev.services.api.models.ServiceRequest
import com.dev.services.api.models.ServiceResult
import com.dev.services.api.repo.ServiceIntegration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber
import java.net.URL
import javax.inject.Inject

class APIKotlinWeekly @Inject constructor(val service: ServiceKotlinWeekly) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        try {
            var latestIssue = request.next
            if (latestIssue == null) {
                latestIssue = getLatestIssue()
            }

            val result =
                service.getFeed("https://mailchi.mp/kotlinweekly/kotlin-weekly-$latestIssue").map { item ->
                    ServiceItem(
                        title = item.title,
                        description = item.description,
                        author = item.issue,
                        topTitleText = item.issue + " ● " + DateUtils.getRelativeTimeSpanString(item.createdAt),
                        likes = item.baseLink,
                        actionUrl = item.link + "?from=aw",
                        sourceType = request.type.toString(),
                        createdAt = item.createdAt,
                        groupId = request.name
                    )
                }
            return ResponseStatus.success(
                ServiceResult(
                    result,
                    (latestIssue.toInt() - 1).toString()
                )
            )
        } catch (exception: Exception) {
            return ResponseStatus.failure(APIErrorException.newInstance(exception))
        }
    }

    private suspend fun getLatestIssue(): String {
        return withContext(Dispatchers.IO) {
            val url = "https://kotlin-weekly.web.app/"
            val doc = Jsoup.parse(URL(url), 30000)

            val issueText =
                doc.select("#latest > div > div.latest-issue > div > h1").text()
            val issueNum = issueText.substring(issueText.lastIndexOf("-") + 1, issueText.length-1)
            Timber.d("latestIssue" + issueNum)
            issueNum
        }
    }
}