package com.dev.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIError(
        @SerializedName("errorCode")
        var errorCode: String? = null,

        @SerializedName("errorMessage")
        var errorMessage: String? = null
) : Parcelable