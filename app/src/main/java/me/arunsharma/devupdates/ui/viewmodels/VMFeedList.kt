package me.arunsharma.devupdates.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.core.utils.StorageUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.models.SourceConfig
import com.devupdates.medium.ServiceMediumRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.utils.cache.AppCache
import me.arunsharma.devupdates.utils.cache.CacheConstants
import me.arunsharma.devupdates.utils.cache.CachingProvider
import javax.inject.Inject

class VMFeedList @Inject constructor(
    private val repoFeed: RepoFeed,
    val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    fun getData(request: ServiceRequest, showLoading: Boolean = true) {
        launchDataLoad {
            if (showLoading) {
                _lvUIState.value = FeedUIState.Loading
            }
            val result = repoFeed.getData(request)
            if (result is ResponseStatus.Success) {
                _lvUIState.value = FeedUIState.ShowList(result.data)
            } else if (result is ResponseStatus.Failure) {
                _lvUIState.value = FeedUIState.ShowError(result.exception)
            }
        }
    }

    fun updateData(currentData: MutableList<ServiceItem>, request: ServiceRequest) {
        if (request.hasPagingSupport) {
            request.next = currentData.last().createdAt
            getData(request, false)
        }
    }

    sealed class FeedUIState {
        object Loading : FeedUIState()
        class ShowList(val list: List<ServiceItem>) : FeedUIState()
        class ShowError(val list: APIErrorException) : FeedUIState()
    }
}