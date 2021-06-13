package me.arunsharma.devupdates.utils

import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.R

fun ServiceRequest.getDrawable(): Int {
    return when (type) {
        DataSource.MEDIUM -> {
            R.drawable.ic_logo_medium
        }
        DataSource.GITHUB -> {
            R.drawable.ic_github
        }
        DataSource.ALL -> {
            R.drawable.ic_home_feed
        }
        else -> {
            R.drawable.ic_rss_feed
        }
    }
}