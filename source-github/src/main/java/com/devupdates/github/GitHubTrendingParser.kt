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

    private fun parseTrendingItem(element: Element): GithubTrendingResponseItem? {
        return try {
            val authorAndName = element.select("h2 > a")
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

            // Prefer selecting by the stargazers/forks link's href, since it's a stable,
            // semantic URL rather than GitHub's frequently-churned Primer CSS utility
            // classes. Fall back to the old class-based selector if that ever comes back.
            val stars = parseCount(element, "a[href$=stargazers]", fallbackIndex = 0)
            val forks = parseCount(element, "a[href$=forks], a[href$=members]", fallbackIndex = 1)

            // "691 stars today"
            val starsToday = element.select(".d-inline-block.float-sm-right").firstOrNull()
                ?.text()
                ?.removeCommas()
                ?.let {
                    NUMBER_PATTERN.find(it)?.groups?.firstOrNull()?.value?.toInt() ?: run {
                        d {  "$authorAndName didn't have today" }
                        null
                    }
                }

            GithubTrendingResponseItem(
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
        } catch (exception: Exception) {
            d { "Skipping trending item, failed to parse: ${exception.message}" }
            null
        }
    }

    /**
     * Reads a "3,441"-style count from [primarySelector] (an href-based selector, stable
     * across GitHub's styling changes), falling back to the [fallbackIndex]-th match of the
     * legacy class-based selector if the primary one finds nothing.
     */
    private fun parseCount(element: Element, primarySelector: String, fallbackIndex: Int): Int? {
        val primary = element.select(primarySelector).firstOrNull()
        val target = primary ?: element.select(".Link--muted.d-inline-block.mr-3").getOrNull(fallbackIndex)
        return target?.text()?.removeCommas()?.toIntOrNull()
    }
}