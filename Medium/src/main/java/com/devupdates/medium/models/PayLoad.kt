package com.devupdates.medium.models

import com.google.gson.annotations.SerializedName

data class PayLoad(
    @SerializedName("value")
    val values: List<Collection>,
    @SerializedName("references")
                   val references: References){

    fun getAuthorName(authorId: String): String? {
        return references.user[authorId]?.name
    }
}