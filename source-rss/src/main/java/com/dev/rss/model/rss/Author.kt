package com.dev.rss.model.rss

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Author(@PropertyElement val name: String)