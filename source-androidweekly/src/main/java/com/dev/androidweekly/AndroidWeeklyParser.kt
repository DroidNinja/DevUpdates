package com.dev.androidweekly

import com.dev.androidweekly.models.AndroidWeeklyFeedItem
import com.dev.core.AppConstants
import com.dev.core.utils.DateTimeHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Copied from Catchup
 */
object AndroidWeeklyParser {

    internal fun parse(body: ResponseBody): List<AndroidWeeklyFeedItem> {
        val fullBody = body.string()
        val doc = Jsoup.parse(fullBody, ServiceAndroidWeekly.ENDPOINT)

        val allList = doc.select("body > div > div > div > div > table")
            .mapNotNull(AndroidWeeklyParser::parseTrendingItem)
            .ifEmpty {
                error("List was empty! Usually this is a sign that parsing failed.")
            }
            .filter {
                it.title.isNotEmpty()
            }.toMutableList()

        return allList.subList(0, allList.indexOfFirst { it.title.contains("Place a") })
    }

    private fun parseTrendingItem(element: Element): AndroidWeeklyFeedItem {
        val title = element.select("tbody > tr > td > div > table > tbody > tr > td > div.text-container.galileo-ap-content-editor > div > div:nth-child(1) > a")
        val baseLink = element.select("tbody > tr > td > div > table > tbody > tr > td > div.text-container.galileo-ap-content-editor > div > div:nth-child(1) > span")
        val description = element.select("tbody > tr > td > div > table > tbody > tr > td > div.text-container.galileo-ap-content-editor > div > div:nth-child(2)")
        val issue = element.root().select("body > div > div > div > h2")
        val date = element.root().select("body > div > div > div > small").text().replace(Regex("(?<=\\d)(st|nd|rd|th)"), "")

        return AndroidWeeklyFeedItem(
            title = title.text(),
            description = description.text(),
            baseLink = baseLink.text(),
            link = title.attr("href"),
            issue = issue.text(),
            createdAt = DateTimeHelper.formatDate(
                AppConstants.FORMAT_DATE,
                date
            )
        )
    }
}