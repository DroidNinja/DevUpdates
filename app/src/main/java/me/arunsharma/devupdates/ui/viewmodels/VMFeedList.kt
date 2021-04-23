package me.arunsharma.devupdates.ui.viewmodels

import android.app.Application
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
                _lvUIState.value = FeedUIState.ShowList(request, result.data)
            } else if (result is ResponseStatus.Failure) {
                _lvUIState.value = FeedUIState.ShowError(result.exception)
            }
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