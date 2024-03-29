package me.arunsharma.devupdates.ui.fragments.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.api.models.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
                })
            }
        }
    }

}