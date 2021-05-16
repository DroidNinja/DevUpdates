package me.arunsharma.devupdates.ui.viewmodels

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.utils.SingleLiveEvent
import javax.inject.Inject

class VMFeedList @Inject constructor(
    private val repoFeed: RepoFeed,
    val sourceConfigStore: SourceConfigStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    val context: Application
) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    val lvShowMessage = SingleLiveEvent<Int>()

    fun getData(
        request: ServiceRequest,
        forceUpdate: Boolean = false,
        showLoading: Boolean = true
    ) {
        launchDataLoad {
            if (showLoading) {
                _lvUIState.value = FeedUIState.Loading
            }
            val result = repoFeed.getData(request, forceUpdate)
            if (result is ResponseStatus.Success) {
                if (!showLoading || result.data.isNotEmpty()) {
                    _lvUIState.value = FeedUIState.ShowList(request, result.data)
                } else {
                    _lvUIState.value = FeedUIState.ShowError(
                        context.getString(R.string.empty_feed),
                        context.getString(R.string.swipe_down_to_refresh_the_feed)
                    )
                }
            } else if (result is ResponseStatus.Failure) {
                _lvUIState.value = FeedUIState.ShowError(result.exception.message)
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
            val result = repoFeed.getHomeFeed(request)
            if (result is ResponseStatus.Success) {
                val data: List<ServiceItem> = result.data.map { item ->
                    var topTitle = item.author
                    if (item.createdAt > 0) {
                        topTitle =
                            topTitle + " ● " + DateUtils.getRelativeTimeSpanString(item.createdAt)
                    }
                    item.topTitleText = topTitle
                    item.likes = item.groupId
                    item
                }
                if (!showLoading || data.isNotEmpty()) {
                    _lvUIState.value = FeedUIState.ShowList(request, data)
                } else {
                    _lvUIState.value = FeedUIState.ShowError(
                        context.getString(R.string.empty_feed),
                        context.getString(R.string.swipe_down_to_refresh_the_feed)
                    )
                }
            } else if (result is ResponseStatus.Failure) {
                _lvUIState.value = FeedUIState.ShowError(result.exception.message)
            }
        }
    }

    fun updateHomeFeedData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = currentData.last().createdAt
            getHomeFeed(request, forceUpdate = false, showLoading = false)
        }
    }

    fun updateData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = currentData.last().createdAt
            getData(request, forceUpdate = false, showLoading = false)
        }
    }

    fun addBookmark(item: ServiceItem) {
        launchDataLoad {
            withContext(ioDispatcher) {
                repoFeed.addBookmark(item)
                if (item.isBookmarked) {
                    lvShowMessage.postValue(R.string.bookmark_added)
                } else {
                    lvShowMessage.postValue(R.string.bookmark_removed)
                }
            }
        }
    }
}