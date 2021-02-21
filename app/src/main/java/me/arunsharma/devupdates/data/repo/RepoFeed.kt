package me.arunsharma.devupdates.data.repo

import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.AppDatabase
import javax.inject.Inject

class RepoFeed @Inject constructor(
    private val serviceIntegration: @JvmSuppressWildcards Map<String, ServiceIntegration>,
    val database: AppDatabase
) {

    suspend fun getData(request: ServiceRequest): ResponseStatus<List<ServiceItem>> {
        return withContext(Dispatchers.IO) {
            val cacheData =
                database.feedDao().getFeedBySource(request.type.toString(), request.getGroupId())
            if (!request.shouldUseCache || cacheData.isNullOrEmpty()) {
                val result =
                    serviceIntegration[request.type.toString()]?.getData(request)
                if (result is ResponseStatus.Success) {
                    if (request.shouldUseCache) {
                        saveCache(result.data)
                    }
                }
                return@withContext result
                    ?: ResponseStatus.failure(APIErrorException.unexpectedError())

            } else {
                return@withContext ResponseStatus.success(cacheData)
            }
        }
    }

    private suspend fun saveCache(data: List<ServiceItem>) {
        database.feedDao().insertAll(data)
    }
}