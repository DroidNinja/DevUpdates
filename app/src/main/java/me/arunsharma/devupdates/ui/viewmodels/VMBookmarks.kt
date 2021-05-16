package me.arunsharma.devupdates.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.AppDatabase
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import javax.inject.Inject

class VMBookmarks @Inject constructor(val database: AppDatabase, val context: Application) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    fun getBookmarks() {
        launchDataLoad {
            _lvUIState.value = FeedUIState.Loading
            withContext(Dispatchers.IO) {
                val data = database.feedDao().getBookmarks()
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
                    _lvUIState.postValue(FeedUIState.ShowError(
                        context.getString(R.string.empty_feed),
                        context.getString(R.string.swipe_down_to_refresh_the_feed)
                    ))
                }
            }
        }
    }
}