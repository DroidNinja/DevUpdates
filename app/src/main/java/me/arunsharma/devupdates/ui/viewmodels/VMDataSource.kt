package me.arunsharma.devupdates.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.SourceConfigStore
import javax.inject.Inject

class VMDataSource @Inject constructor(
    val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private val _lvFetchConfig = MutableLiveData<List<ServiceRequest>>()
    val lvFetchConfig: LiveData<List<ServiceRequest>> = _lvFetchConfig


    fun getServices() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val configList = sourceConfigStore.getData()
                _lvFetchConfig.postValue(configList)
            }
        }
    }
}