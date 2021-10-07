package me.arunsharma.devupdates.compose

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dev.core.di.annotations.IoDispatcher
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeedList
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    override val context: Application
) :
    VMFeedList(repoFeed, ioDispatcher, context) {

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
}