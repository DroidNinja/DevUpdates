package com.dev.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class APIError(
        var errorCode: String? = null,

        var errorMessage: String? = null
) : Parcelable