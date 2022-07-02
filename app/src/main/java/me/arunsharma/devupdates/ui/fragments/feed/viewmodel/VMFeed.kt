package me.arunsharma.devupdates.ui.fragments.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.core.base.BaseViewModel
import com.dev.services.models.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.utils.getDrawable
import javax.inject.Inject

@HiltViewModel
class VMFeed @Inject constructor(
    val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private val _lvFetchConfig = MutableLiveData<List<FeedPagerItem>>()
    val lvFetchConfig: LiveData<List<FeedPagerItem>> = _lvFetchConfig

    private val _flowFetchConfig = MutableStateFlow<List<FeedPagerItem>>(mutableListOf())
    val flowFetchConfig: StateFlow<List<FeedPagerItem>>
        get() = _flowFetchConfig.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = mutableListOf(),
        )

    init {
        getConfig()
    }

    fun getConfig() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val configList = sourceConfigStore.fetchFromRemote()
                _lvFetchConfig.postValue(configList.map { item ->
                    if (item.type == DataSource.MEDIUM) {
                        item.next = System.currentTimeMillis().toString()
                    }
                    FeedPagerItem(
                        item.getDrawable(),
                        item
                    )
                }

                _flowFetchConfig.emit(configList)
                _lvFetchConfig.postValue(configList)
            }
        }
    }

}