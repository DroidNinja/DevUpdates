package com.dev.rss.model.blogspot

import androidx.annotation.Keep
import com.tickaroo.tikxml.TypeConverter
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
  @PropertyElement(name= "feedburner:origLink")
  val link: String?,
  @PropertyElement(converter = HtmlEscapeStringConverter::class)
  val content: String?,
  @PropertyElement
  val updated: String?
)
