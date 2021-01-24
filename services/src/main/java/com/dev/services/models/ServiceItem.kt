package com.dev.services.models

import android.text.SpannedString

data class ServiceItem(
    var title: String,
    var description: String? = null,
    var author: SpannedString? = null,
    var likes: String?,
    var actionUrl: String?
)