package me.arunsharma.devupdates.compose.home

import androidx.compose.runtime.Composable
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem

data class Page(
    val item: FeedPagerItem,
    val content: @Composable () -> Unit,
)