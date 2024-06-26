package me.arunsharma.devupdates.ui.fragments.bookmarks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.core.di.annotations.IoDispatcher
import com.dev.services.api.models.DataSource
import com.dev.services.api.models.ServiceItem
import com.dev.services.api.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.utils.BookmarkEvent
import me.arunsharma.devupdates.utils.EventBus
import com.dev.core.utils.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VMBookmarks @Inject constructor(
    val repoFeed: RepoFeed,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    val eventBus: EventBus,
    val context: Application
) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    val lvShowMessage = SingleLiveEvent<Int>()

    fun getBookmarks(forceUpdate: Boolean = false) {
        launchDataLoad {
            if (!forceUpdate) {
                _lvUIState.value = FeedUIState.Loading
            }
            Timber.d("get Bookmarks ")
            withContext(Dispatchers.IO) {
                repoFeed.getBookmarks().distinctUntilChanged().collect { data ->
                    Timber.d("Bookmarks changed" + data.size)
                    if (data.isNotEmpty()) {
                        _lvUIState.postValue(
                            FeedUIState.ShowList(
                                ServiceRequest(
                                    DataSource.BLOGSPOT,
                                    "BM"
                                ), data
                            )
                        )
                    } else {
                        _lvUIState.postValue(
                            FeedUIState.ShowError(
                                context.getString(com.dev.core.R.string.empty_feed),
                                context.getString(com.dev.core.R.string.swipe_down_to_refresh_the_feed)
                            )
                        )
                    }
                }
            }
        }
    }

    fun removeBookmark(item: ServiceItem) {
        launchDataLoad {
            withContext(ioDispatcher) {
                item.isBookmarked = false
                repoFeed.addBookmark(item)
                lvShowMessage.postValue(R.string.bookmark_removed)
                eventBus.produceEvent(BookmarkEvent(item))
            }
        }
    }
}