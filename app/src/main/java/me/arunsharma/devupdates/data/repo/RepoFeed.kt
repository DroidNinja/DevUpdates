package me.arunsharma.devupdates.data.repo

import android.text.format.DateUtils
import com.dev.core.di.annotations.IoDispatcher
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.AppDatabase
import timber.log.Timber
import javax.inject.Inject

class RepoFeed @Inject constructor(
    private val serviceIntegration: @JvmSuppressWildcards Map<String, ServiceIntegration>,
    val database: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getData(
        request: ServiceRequest,
        forceUpdate: Boolean
    ): ResponseStatus<List<ServiceItem>> {
        try {
            return withContext(ioDispatcher) {
                val cacheData =
                    database.feedDao()
                        .getFeedBySource(
                            request.type.toString(),
                            request.getGroupId(),
                            request.next
                        )
                if (forceUpdate || !request.shouldUseCache || cacheData.isNullOrEmpty()) {
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
        } catch (ex: Exception) {
            return ResponseStatus.failure(APIErrorException.newInstance(ex))
        }
    }

    suspend fun getHomeFeed(request: ServiceRequest): ResponseStatus<List<ServiceItem>> {
        try {
            return withContext(ioDispatcher) {
                val cacheData =
                    database.feedDao()
                        .getAllFeed(request.next)

                return@withContext ResponseStatus.success(mapHomeFeed(cacheData))
            }
        } catch (ex: Exception) {
            return ResponseStatus.failure(APIErrorException.newInstance(ex))
        }
    }

    private fun mapHomeFeed(data : List<ServiceItem>): List<ServiceItem> {
        return data.map { item ->
            var topTitle = item.author
            if (item.createdAt > 0) {
                topTitle =
                    topTitle + " ● " + DateUtils.getRelativeTimeSpanString(item.createdAt)
            }
            item.topTitleText = topTitle
            item.likes = item.groupId
            item
        }
    }

    suspend fun observeHomeFeed(onNewData: suspend (List<ServiceItem>) -> Unit) {
        try {
            return withContext(ioDispatcher) {
                database.feedDao()
                    .observeFeed().distinctUntilChanged().collect { data->
                        if(data.isNotEmpty()) {
                            onNewData(data)
                        }
                    }
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun saveCache(data: List<ServiceItem>) {
        database.feedDao().insertAll(data)
    }

    suspend fun addBookmark(item: ServiceItem) {
        withContext(ioDispatcher) {
            database.feedDao().setBookmark(item.isBookmarked, item.actionUrl)
        }
    }
}