package me.arunsharma.devupdates.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.models.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.ui.fragments.feed.FeedPagerItem
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
                val configList = sourceConfigStore.getData()
                _lvFetchConfig.postValue(configList.map { item ->
                    if (item.type == DataSource.MEDIUM) {
                        item.next = System.currentTimeMillis()
                        FeedPagerItem(
                            R.drawable.ic_logo_medium,
                            item
                        )
                    } else if (item.type == DataSource.GITHUB) {
                        FeedPagerItem(
                            R.drawable.ic_github,
                            item
                        )
                    }
                    else if (item.type == DataSource.ALL) {
                        FeedPagerItem(
                            R.drawable.ic_home_feed,
                            item
                        )
                    } else {
                        FeedPagerItem(
                            R.drawable.ic_rss_feed,
                            item
                        )
                    }
                })
            }
        }
    }

}