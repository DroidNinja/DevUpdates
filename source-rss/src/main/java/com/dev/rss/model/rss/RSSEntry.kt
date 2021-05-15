package com.dev.rss.model.rss

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Keep
@Xml(name = "entry")
data class RSSEntry(
    @PropertyElement(converter = HtmlEscapeStringConverter::class)
    val title: String?,
    @PropertyElement
    val id: String?,
    @Element
    val link: RSSLink?,
    @PropertyElement(converter = HtmlEscapeStringConverter::class)
    val summary: String?,
    @Element
    val author: Author?,
    @PropertyElement
    val updated: String?
)
