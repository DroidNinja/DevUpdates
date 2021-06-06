package me.arunsharma.devupdates.ui.fragments.feed

import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest

sealed class FeedUIState {
    object Loading : FeedUIState()
    object HasNewItems : FeedUIState()
    class ShowList(val request: ServiceRequest, val list: List<ServiceItem>) : FeedUIState()
    class ShowError(val errorTitle: String?, val errorSubtitle: String? = null) : FeedUIState()
}