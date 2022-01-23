package me.arunsharma.devupdates.ui.fragments.feed.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
open class VMFeedList @Inject constructor(
    private val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    open val context: Application
) :
    BaseViewModel() {

    private var pageId: String? = null
    protected open val _lvUIState = MutableLiveData<FeedUIState>()
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
                if (result.data.data.isNotEmpty()) {
                    pageId = result.data.page
                    _lvUIState.value = FeedUIState.ShowList(request, result.data.data)
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

    fun updateData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = pageId ?: currentData.last().createdAt.toString()
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