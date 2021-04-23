package com.devupdates.github

import com.dev.core.extensions.d
import com.devupdates.github.models.GithubTrendingResponseItem
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Copied from Catchup
 */
object GitHubTrendingParser {
    private val NUMBER_PATTERN = "\\d+".toRegex()
    private fun String.removeCommas() = replace(",", "")

    internal fun parse(body: ResponseBody): List<GithubTrendingResponseItem> {
        val fullBody = body.string()
        return Jsoup.parse(fullBody, ServiceGithub.ENDPOINT)
            .getElementsByClass("Box-row")
            .mapNotNull(GitHubTrendingParser::parseTrendingItem)
            .ifEmpty {
                error("List was empty! Usually this is a sign that parsing failed.")
            }
    }

    private fun parseTrendingItem(element: Element): GithubTrendingResponseItem {
        val authorAndName = element.select("h1 > a")
            .attr("href")
            .toString()
            .removePrefix("/")
            .trimEnd()
            .split("/")
            .let { Pair(it[0], it[1]) }
        val (author, repoName) = authorAndName
        val url = "${ServiceGithub.ENDPOINT}/${authorAndName.first}/${authorAndName.second}"
        val description = element.select("p").text()

        val language = element.select("[itemprop=\"programmingLanguage\"]").text()
        // "background-color:#563d7c;"
        val languageColor = element.select(".repo-language-color")
            .firstOrNull()
            ?.attr("style")
            ?.removePrefix("background-color:")
            ?.trimStart() // Thanks for the leading space, GitHub
            ?.let {
                val colorSubstring = it.removePrefix("#")
                if (colorSubstring.length == 3) {
                    // Three digit hex, convert to 6 digits for Color.parseColor()
                    "#${colorSubstring.replace(".".toRegex(), "$0$0")}"
                } else {
                    it
                }
            }

        // "3,441" stars, forks
        val counts = element.select(".Link--muted.d-inline-block.mr-3")
            .asSequence()
            .map(Element::text)
            .map { it.removeCommas() }
            .map(String::toInt)
            .toList()

        val stars = counts[0]
        val forks = counts.getOrNull(1)

        // "691 stars today"
        val starsToday = element.select(".f6.color-text-secondary.mt-2 > span:last-child")[0]
            .text()
            .removeCommas()
            .let {
                NUMBER_PATTERN.find(it)?.groups?.firstOrNull()?.value?.toInt() ?: run {
                    d {  "$authorAndName didn't have today" }
                    null
                }
            }

        return GithubTrendingResponseItem(
            author = author,
            url = url,
            name = repoName,
            description = description,
            stars = stars,
            forks = forks,
            currentPeriodStars = starsToday,
            language = language,
            languageColor = languageColor
        )
    }
}