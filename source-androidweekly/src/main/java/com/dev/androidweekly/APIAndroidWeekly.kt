package com.dev.androidweekly

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.models.ServiceResult
import com.dev.services.repo.ServiceIntegration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber
import java.net.URL
import javax.inject.Inject

class APIAndroidWeekly @Inject constructor(val service: ServiceAndroidWeekly) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        try {
            var latestIssue = request.next
            if (latestIssue == null) {
                latestIssue = getLatestIssue()
            }

            val result =
                service.getFeed("https://androidweekly.net/issues/issue-$latestIssue").map { item ->
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
            val url = "https://androidweekly.net/archive"
            val doc = Jsoup.parse(URL(url), 30000)

            val issueText =
                doc.select("body > section > div > div > ul > li:nth-child(1) > h3 > a").text()
            Timber.d("latestIssue" + issueText)
            issueText.substring(issueText.lastIndexOf("#") + 1, issueText.length)
        }
    }
}