package me.arunsharma.devupdates.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import com.devupdates.medium.ServiceMedium
import com.devupdates.medium.ServiceMediumRequest
import javax.inject.Inject

class VMFeed @Inject constructor(private val serviceIntegration: @JvmSuppressWildcards Map<String, ServiceIntegration>) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    fun getData(request: ServiceRequest) {
        launchDataLoad {
            _lvUIState.value = FeedUIState.Loading
            val result = serviceIntegration[request.type.toString()]?.getData(request) ?: mutableListOf()
            _lvUIState.value = FeedUIState.ShowList(result)
        }
    }

    sealed class FeedUIState {
        object Loading : FeedUIState()
        class ShowList(val list: List<ServiceItem>) : FeedUIState()
    }
}