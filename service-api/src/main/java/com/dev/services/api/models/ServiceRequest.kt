package com.dev.services.api.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

interface Pageable {
    val hasPagingSupport: Boolean
    var next: String?
}

@JsonClass(generateAdapter = true)
@Parcelize
open class ServiceRequest(
    val type: DataSource,
    val name: String,
    val shouldUseCache: Boolean = false,
    val metadata: MutableMap<String, String?>? = null,
    override var next: String? = null,
    override val hasPagingSupport: Boolean = false
) : Parcelable, Pageable {

    fun getGroupId(): String {
        return name
    }
}