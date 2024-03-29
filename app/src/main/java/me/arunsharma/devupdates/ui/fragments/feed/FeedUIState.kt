package me.arunsharma.devupdates.ui.fragments.feed

import com.dev.services.api.models.ServiceItem
import com.dev.services.api.models.ServiceRequest

sealed class FeedUIState {
    object Loading : FeedUIState()
    object HasNewItems : FeedUIState()
    object RefreshList : FeedUIState()
    class ShowList(val request: ServiceRequest, val list: List<ServiceItem>) : FeedUIState()
    class ShowError(val errorTitle: String?, val errorSubtitle: String? = null) : FeedUIState()
}