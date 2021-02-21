package com.devupdates.medium.models

import com.google.gson.annotations.SerializedName

data class PayLoad(
    @SerializedName("value")
    val values: List<Collection>,
    @SerializedName("references")
    val references: References,
    @SerializedName("streamItems")
    val streamItems: List<StreamItem>
) {

    fun getAuthorName(authorId: String): String? {
        return references.user[authorId]?.name
    }

    fun getTaggedPosts(): List<Collection?> {
        return streamItems.map {
            references.post[it.postPreview.postId]
        }
    }
}