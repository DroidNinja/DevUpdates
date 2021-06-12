package com.dev.services.models

import android.os.Parcelable
import com.dev.services.R
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

interface Pageable {
    var hasPagingSupport: Boolean
    var next: Long
}

@JsonClass(generateAdapter = true)
@Parcelize
open class ServiceRequest(
    var type: DataSource,
    var name: String,
    var shouldUseCache: Boolean = false,
    var metadata: MutableMap<String, String?>? = null,
    override var next: Long = 0,
    override var hasPagingSupport: Boolean = false
) : Parcelable, Pageable {

    fun getGroupId(): String {
        return name
    }
}