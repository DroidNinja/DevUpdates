package com.devupdates.medium.models

import com.google.gson.annotations.SerializedName

data class References(
    @SerializedName("User")
    val user: Map<String, User>)