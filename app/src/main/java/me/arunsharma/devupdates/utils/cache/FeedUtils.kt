package me.arunsharma.devupdates.utils.cache

import com.dev.services.models.DataSource
import me.arunsharma.devupdates.R

object FeedUtils {
    fun getDrawable(type: DataSource): Int {
        return when (type) {
            DataSource.GITHUB -> {
                R.drawable.ic_github
            }
            DataSource.MEDIUM -> {
                R.drawable.ic_logo_medium
            }
            else -> {
                R.drawable.ic_rss_feed
            }
        }
    }
}