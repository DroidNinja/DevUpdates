package com.dev.rss.model.rss

import androidx.annotation.Keep
import com.tickaroo.tikxml.TypeConverter
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Keep
@Xml(name="link")
data class RSSLink(
  @Attribute
  val href : String? = null
)
