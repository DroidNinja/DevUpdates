package com.devupdates.medium

import android.os.Parcelable
import com.dev.services.models.DataSource
import com.dev.services.models.Pageable
import com.dev.services.models.ServiceRequest
import kotlinx.parcelize.Parcelize


class ServiceMediumRequest(
    type: DataSource,
    name: String,
    var username: String,
    var tag: String? = null,
    override var next: Long = 0,
) : ServiceRequest(type, name), Pageable, Parcelable