package me.arunsharma.devupdates.ui.fragments.feed

import com.dev.network.model.APIErrorException
import com.dev.services.models.ServiceItem

sealed class FeedUIState {
        object Loading : FeedUIState()
        class ShowList(val list: List<ServiceItem>) : FeedUIState()
        class ShowError(val list: APIErrorException) : FeedUIState()
    }