package me.arunsharma.devupdates.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.core.base.BaseViewModel
import com.dev.services.github.ServiceGithub
import com.dev.services.models.ServiceItem
import com.dev.services.repo.ServiceIntegration
import javax.inject.Inject

class VMFeed @Inject constructor(private val serviceIntegration: @JvmSuppressWildcards Map<String, ServiceIntegration>) :
    BaseViewModel() {

    private val _lvUIState = MutableLiveData<FeedUIState>()
    val lvUiState: LiveData<FeedUIState> = _lvUIState

    fun getData() {
        launchDataLoad {
            val result = serviceIntegration[ServiceGithub.SERVICE_KEY]?.getData() ?: mutableListOf()
            _lvUIState.value = FeedUIState.ShowList(result)
        }
    }

    sealed class FeedUIState {
        object Loading : FeedUIState()
        class ShowList(val list: List<ServiceItem>) : FeedUIState()
    }
}