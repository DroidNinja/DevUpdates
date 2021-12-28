package com.dev.rss.model.blogspot

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Keep
@Xml
data class Entry(
    @PropertyElement(converter = HtmlEscapeStringConverter::class)
    val title: String?,
    @PropertyElement
    val id: String?,
    @Element
    val link: List<Link>?,
    @PropertyElement(converter = HtmlEscapeStringConverter::class)
    val content: String?,
    @PropertyElement(name = "published")
    val updated: String?
) {

    fun getURL(): String? {
        return link?.find {
            it.type == "text/html"
        }?.href
    }
}

@Xml(name = "link")
data class Link(
    @Attribute(name = "type") val type: String?,
    @Attribute(name = "href") val href: String?
)
