package com.dev.rss.model.blogspot

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Feed(
    @Element
    val itemList: List<Entry>,

    @PropertyElement
    val title: String? = null,

    @PropertyElement
    val language: String? = null,

    @PropertyElement
    val updated: String? = null
)
