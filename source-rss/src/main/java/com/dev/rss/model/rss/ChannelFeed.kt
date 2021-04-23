package com.dev.rss.model.rss

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Xml(name="rss")
data class ChannelFeed(
    @Element
    val channel: Channel,

    @PropertyElement
    val title: String? = null,

    @PropertyElement
    val language: String? = null,

    @PropertyElement
    val updated: String? = null
)


@Xml(name="channel")
data class Channel(
    @Element
    val item: List<ChannelEntry>,

    @PropertyElement
    val title: String? = null,

    @PropertyElement
    val language: String? = null,

    @PropertyElement
    val updated: String? = null
)

@Keep
@Xml(name="item")
data class ChannelEntry(
    @PropertyElement(converter = HtmlEscapeStringConverter::class)
    val title: String?,
    @PropertyElement
    val id: String?,
    @PropertyElement
    val link: String?,
    @PropertyElement(name= "description", converter = HtmlEscapeStringConverter::class)
    val summary: String?,
    @PropertyElement(name="dc:creator")
    val author: String?,
    @PropertyElement
    val pubDate: String?
)
