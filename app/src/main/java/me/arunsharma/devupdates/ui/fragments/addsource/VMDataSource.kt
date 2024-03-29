package me.arunsharma.devupdates.ui.fragments.addsource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.api.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.SourceConfigStore
import javax.inject.Inject

@HiltViewModel
class VMDataSource @Inject constructor(
    val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private val _lvFetchConfig = MutableLiveData<List<ServiceRequest>>()
    val lvFetchConfig: LiveData<List<ServiceRequest>> = _lvFetchConfig


    fun getServices() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val configList = sourceConfigStore.fetchFromRemote()
                _lvFetchConfig.postValue(configList)
            }
        }
    }

    fun saveConfig(data: List<ServiceRequest>) {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                sourceConfigStore.save(data = data)
            }
        }
    }

    fun deleteSource(item: ServiceRequest) {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val data = sourceConfigStore.get()
                data.removeAll { req ->
                    req.type == item.type && req.name == item.name
                }
                if(data.isNotEmpty()) {
                    sourceConfigStore.save(data = data)
                }
            }
        }
    }
}