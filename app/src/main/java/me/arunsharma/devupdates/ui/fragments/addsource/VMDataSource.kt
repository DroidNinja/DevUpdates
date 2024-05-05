package me.arunsharma.devupdates.ui.fragments.addsource

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.core.model.ErrorException
import com.dev.services.api.models.DataSource
import com.dev.services.api.models.ServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import com.dev.core.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class VMDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private val _lvFetchConfig = MutableLiveData<List<ServiceRequest>>()
    val lvFetchConfig: LiveData<List<ServiceRequest>> = _lvFetchConfig

    private val _onDataSourceAdded = SingleLiveEvent<String>()
    val onDataSourceAdded: LiveData<String> = _onDataSourceAdded


    fun getServices() {
        launchDataLoad {
            val configList = sourceConfigStore.fetchFromRemote()
            _lvFetchConfig.value = configList
        }
    }

    fun saveConfig(data: List<ServiceRequest>) {
        launchDataLoad {
            sourceConfigStore.save(data = data)
        }
    }

    fun deleteSource(item: ServiceRequest) {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                val data = sourceConfigStore.get()
                data.removeAll { req ->
                    req.type == item.type && req.name == item.name
                }
                if (data.isNotEmpty()) {
                    sourceConfigStore.save(data = data)
                }
            }
        }
    }

    private fun validate(name: String, url: String): Boolean {
        return name.isNotEmpty() && url.isNotEmpty()
    }

    fun addSource(type: Int, name: String, url: String) {
        launchDataLoad {
            if (validate(name, url)) {
                val request = when (type) {
                    R.id.chipMedium -> onMediumSelected(name, url)
                    R.id.chipRss -> onRSSSelected(name, url)
                    else -> null
                }
                request?.let {
                    _onDataSourceAdded.value = it.name + " added!!"
                    // update list
                    getServices()
                }
            } else {
                _lvError.value =
                    ErrorException.unexpectedError(context.getString(R.string.error_invalid_input))
            }

        }
    }

    private suspend fun onRSSSelected(name: String, url: String): ServiceRequest? =
        withContext(Dispatchers.IO) {
            val request = ServiceRequest(
                type = DataSource.RSS_CHANNEL,
                name = name,
                metadata = mutableMapOf(),
                shouldUseCache = true,
                hasPagingSupport = true
            )
            request.metadata?.put("url", url)
            sourceConfigStore.addSource(request)
            return@withContext request
        }

    private suspend fun onMediumSelected(name: String, url: String): ServiceRequest? =
        withContext(Dispatchers.IO) {
            val uri = Uri.parse(url)
            if (uri != null && uri.host == "medium.com") {
                val request = ServiceRequest(
                    type = DataSource.MEDIUM,
                    name = name,
                    metadata = mutableMapOf(),
                    shouldUseCache = true,
                    hasPagingSupport = true
                )
                val urlParts = uri.path?.split("/")
                if (urlParts != null && urlParts.size > 2) {
                    request.metadata?.put("username", urlParts[1])
                    if (url.contains("tagged")) {
                        request.metadata?.put("tag", urlParts[urlParts.indexOf("tagged") + 1])
                    }
                }
                sourceConfigStore.addSource(request)
                return@withContext request
            }
            return@withContext null
        }
}