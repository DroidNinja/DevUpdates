package com.dev.services.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SourceConfig(
    val data: List<ServiceRequest>
)