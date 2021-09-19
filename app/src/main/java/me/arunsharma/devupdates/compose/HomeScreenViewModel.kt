package me.arunsharma.devupdates.compose

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dev.core.base.BaseViewModel
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    val context: Application
) :
    BaseViewModel() {

    private val currentFeedList = mutableListOf<ServiceItem>()

    private val _state = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val state: StateFlow<FeedUIState>
        get() = _state.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FeedUIState.Loading,
        )

    fun observeHomeFeed(
        request: ServiceRequest,
    ) {
        launchDataLoad {
            _state.value = FeedUIState.Loading
            repoFeed.observeHomeFeed { data ->
//                if (currentFeedList.size != data.size) {
                    if (_state.value is FeedUIState.ShowList && currentFeedList.isNotEmpty()) {
                        if (data.first().createdAt > currentFeedList.first().createdAt) {
                            _state.emit(FeedUIState.HasNewItems)
                        }
                    } else {
                        Timber.e("fetchHomeFeed")

                        request.next = System.currentTimeMillis()
                        currentFeedList.addAll(data)
                        _state.emit(FeedUIState.ShowList(request, data))
                    }
//                }
            }
        }
    }

    fun getData(
        request: ServiceRequest,
        forceUpdate: Boolean = false,
        showLoading: Boolean = true
    ) {
        launchDataLoad {
            if (showLoading) {
                _state.emit(FeedUIState.Loading)
                request.next = System.currentTimeMillis()
            }
            val result = repoFeed.getData(request, forceUpdate)
            if (result is ResponseStatus.Success) {
                if (!showLoading || result.data.isNotEmpty()) {
                    _state.emit(FeedUIState.ShowList(request, result.data))
                } else {
                    _state.emit(FeedUIState.ShowError(
                        context.getString(R.string.empty_feed),
                        context.getString(R.string.swipe_down_to_refresh_the_feed)
                    ))
                }
            } else if (result is ResponseStatus.Failure) {
                _state.emit(FeedUIState.ShowError(result.exception.message))
            }
        }
    }
}