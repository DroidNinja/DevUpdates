package me.arunsharma.devupdates.ui.fragments.addsource

import android.content.Context
import android.net.Uri
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
import javax.inject.Inject

@HiltViewModel
class VMAddSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val sourceConfigStore: SourceConfigStore
) :
    BaseViewModel() {

    private fun validate(name: String, url: String): Boolean {
        return name.isNotEmpty() && url.isNotEmpty()
    }

    fun addSource(type: Int, name: String, url: String) {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                if(validate(name, url)) {
                    when (type) {
                        R.id.chipMedium -> onMediumSelected(name, url)
                        R.id.chipRss -> onRSSSelected(name, url)
                    }
                }
                else{
                    _lvError.value = ErrorException.unexpectedError(context.getString(R.string.error_invalid_input))
                }
            }
        }
    }

    private fun onRSSSelected(name: String, url: String) {

    }

    private suspend fun onMediumSelected(name: String, url: String) {
        val uri = Uri.parse(url)
        if(uri != null && uri.host == "medium.com") {
            val request = ServiceRequest(
                type = DataSource.MEDIUM,
                name = name,
                metadata = mutableMapOf(),
                shouldUseCache = true,
                hasPagingSupport = true
            )
            val urlParts = uri.path?.split("/")
            if (urlParts!= null && urlParts.size > 2) {
                request.metadata?.put("username", urlParts[1])
                if(url.contains("tagged")) {
                    request.metadata?.put("tag", urlParts[urlParts.indexOf("tagged") + 1])
                }
            }
            sourceConfigStore.addSource(request)
        }

    }
}