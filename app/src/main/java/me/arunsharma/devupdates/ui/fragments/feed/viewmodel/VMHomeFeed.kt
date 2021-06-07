package me.arunsharma.devupdates.ui.fragments.feed.viewmodel

import android.app.Application
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VMHomeFeed @Inject constructor(
    private val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    override val context: Application
) :
    VMFeedList(repoFeed, ioDispatcher, context) {

    private val currentFeedList = mutableListOf<ServiceItem>()

    fun observeHomeFeed(
        request: ServiceRequest,
    ) {
        launchDataLoad {
            _lvUIState.value = FeedUIState.Loading
            repoFeed.observeHomeFeed { data ->
                if (_lvUIState.value is FeedUIState.ShowList && currentFeedList.isNotEmpty()) {
                    if (data.first().createdAt > currentFeedList.first().createdAt) {
                        _lvUIState.postValue(FeedUIState.HasNewItems)
                    }
                } else {
                    Timber.e("fetchHomeFeed")
                    fetchHomeFeed(request)
                }
            }
        }
    }

    fun getHomeFeed(
        request: ServiceRequest,
        forceUpdate: Boolean = false,
        showLoading: Boolean = true
    ) {
        launchDataLoad {
            if (showLoading) {
                _lvUIState.value = FeedUIState.Loading
            }
            if (forceUpdate) {
                request.next = System.currentTimeMillis()
            }
            fetchHomeFeed(request, showLoading)
        }
    }

    private suspend fun fetchHomeFeed(
        request: ServiceRequest,
        showLoading: Boolean = true
    ) {
        val result = repoFeed.getHomeFeed(request)
        if (result is ResponseStatus.Success) {
            if (!showLoading || result.data.isNotEmpty()) {
                currentFeedList.addAll(result.data)
                _lvUIState.postValue(FeedUIState.ShowList(request, result.data))
            } else {
                _lvUIState.postValue(
                    FeedUIState.ShowError(
                        context.getString(R.string.empty_feed),
                        context.getString(R.string.swipe_down_to_refresh_the_feed)
                    )
                )
            }
        } else if (result is ResponseStatus.Failure) {
            _lvUIState.postValue(FeedUIState.ShowError(result.exception.message))
        }
    }

    fun updateHomeFeedData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = currentData.last().createdAt
            getHomeFeed(request, forceUpdate = false, showLoading = false)
        }
    }
}