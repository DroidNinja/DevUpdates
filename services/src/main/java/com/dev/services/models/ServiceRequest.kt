package com.dev.services.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class ServiceRequest(var type: DataSource): Parcelable