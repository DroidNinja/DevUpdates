package com.dev.rss.model.blogspot

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="feed")
data class Feed(
    @Element(name = "entry")
    val itemList: List<Entry>? = mutableListOf(),

    @PropertyElement
    val title: String? = null,

    @PropertyElement
    val language: String? = null,

    @PropertyElement
    val updated: String? = null
)
