package me.arunsharma.devupdates.ui.fragments.feed.viewmodel

import android.app.Application
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import javax.inject.Inject

@HiltViewModel
class VMHomeFeed @Inject constructor(
    private val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    override val context: Application
) :
    VMFeedList(repoFeed, ioDispatcher, context) {

    private val currentFeedList = mutableListOf<ServiceItem>()

    val feedItems = repoFeed.observeHomeFeed()

    fun onDataReceived(
        data: List<ServiceItem>,
        request: ServiceRequest
    ) {
        launchDataLoad {
            if (data.size != currentFeedList.size) {
                if (_lvUIState.value is FeedUIState.ShowList && currentFeedList.isNotEmpty()) {
                    if (data.first().createdAt > currentFeedList.first().createdAt) {
                        _lvUIState.postValue(FeedUIState.HasNewItems)
                    }
                } else {
                    getHomeFeed(request, true)
                }
            }
        }
    }

    fun getHomeFeed(
        request: ServiceRequest,
        forceUpdate: Boolean = false
    ) {
        launchDataLoad {
            if (forceUpdate) {
                _lvUIState.value = FeedUIState.Loading
            }
            val result = repoFeed.getHomeFeed(request)
            if (result is ResponseStatus.Success) {
                if (result.data.isNotEmpty()) {
                    currentFeedList.addAll(result.data)
                }
                _lvUIState.postValue(
                    FeedUIState.ShowList(
                        request,
                        result.data)
                )
            } else if (result is ResponseStatus.Failure) {
                _lvUIState.postValue(FeedUIState.ShowError(result.exception.message))
            }
        }
    }

    fun updateHomeFeedData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = currentData.last().createdAt.toString()
            getHomeFeed(request, forceUpdate = false)
        }
    }
}