package com.dev.services.api.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

interface Pageable {
    var hasPagingSupport: Boolean
    var next: String?
}

@JsonClass(generateAdapter = true)
@Parcelize
open class ServiceRequest(
    var type: DataSource,
    var name: String,
    var shouldUseCache: Boolean = false,
    var metadata: MutableMap<String, String?>? = null,
    override var next: String? = null,
    override var hasPagingSupport: Boolean = false
) : Parcelable, Pageable {

    fun getGroupId(): String {
        return name
    }
}