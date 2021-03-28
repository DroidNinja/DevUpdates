package me.arunsharma.devupdates.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.network.model.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.AppDatabase
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import javax.inject.Inject

class VMBookmarks @Inject constructor(val database: AppDatabase): BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    fun getBookmarks(){
        launchDataLoad {
            _lvUIState.value = FeedUIState.Loading
            withContext(Dispatchers.IO){
                val data = database.feedDao().getBookmarks()
                _lvUIState.postValue(FeedUIState.ShowList(data))
            }
        }
    }
}