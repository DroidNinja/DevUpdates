package com.dev.rss.model.rss

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="feed")
data class RSSFeed(
    @Element
    val entry: List<RSSEntry>,

    @PropertyElement
    val title: String? = null,

    @PropertyElement
    val language: String? = null,

    @PropertyElement
    val updated: String? = null
)
