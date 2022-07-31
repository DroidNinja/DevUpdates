package com.dev.kotlinweekly

import com.dev.core.AppConstants
import com.dev.core.utils.DateTimeHelper
import com.dev.kotlinweekly.models.KotlinWeeklyFeedItem
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 * Copied from Catchup
 */
object KotlinWeeklyParser {

    internal fun parse(body: ResponseBody): List<KotlinWeeklyFeedItem> {
        val fullBody = body.string()
        val doc = Jsoup.parse(fullBody, ServiceKotlinWeekly.ENDPOINT)

        val issue = doc.select("#templateBody > table > tbody > tr > td > table > tbody > tr > td > h1 > span > span > span")
            .text()
        val date = doc.select("#templateBody > table > tbody > tr > td > table > tbody > tr > td > div:nth-child(2) > span > span > span")
            .text().replace(Regex("((?<=\\d)(st|nd|rd|th) of)"), "")
        val allList = doc.select("#templateBody > table > tbody > tr > td > table > tbody > tr > td > div")
        return parseTrendingItem(allList, issue, date)
    }

    private fun parseTrendingItem(elements: Elements, issue: String, date: String): MutableList<KotlinWeeklyFeedItem> {
        val items = mutableListOf<KotlinWeeklyFeedItem>()
        elements.iterator().forEach {element->
            val baseLink = element.select("p")

            val itemsDiv = element.select("div:nth-child(2)")
            itemsDiv.iterator().forEach { item->
                val storyItems = item.select("a > span")
                storyItems.forEachIndexed { index, storyItem->
                    val title = item.select("a > span").get(index).text()
                    val description = item.select("span[style=font-size:14px]").get(index).text()
                    val domain = item.select("a > strong").get(index).text()
                    val link = item.select("a").get(index*2).attr("href")

                    items.add(KotlinWeeklyFeedItem(
                        title = title,
                        description = description,
                        baseLink = baseLink.text() + " ● " + domain,
                        link = link,
                        issue = "Issue $issue",
                        createdAt = DateTimeHelper.formatDate(
                            AppConstants.FORMAT_DATE_2,
                            date
                        )
                    ))
                }
            }
        }
        return items
    }
}