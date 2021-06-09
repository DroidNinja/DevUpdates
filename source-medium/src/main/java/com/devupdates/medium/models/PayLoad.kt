package com.devupdates.medium.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PayLoad(
    @Json(name = "value")
    var values: List<Collection>?,
    @Json(name = "references")
    val references: References?,
    @Json(name = "streamItems")
    val streamItems: List<StreamItem>?
) {

    fun getAuthorName(authorId: String): String? {
        return references?.user?.get(authorId)?.name
    }

    fun getTaggedPosts(): List<Collection?>? {
        return streamItems?.map {
            references?.post?.get(it.postPreview.postId)
        }
    }
}