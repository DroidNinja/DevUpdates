package me.arunsharma.devupdates.workers

import com.dev.core.di.annotations.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.utils.NotificationUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * Forces a refresh in the data repository.
 */
open class RefreshFeedSources @Inject constructor(
    private val repository: RepoFeed,
    private val sourceConfigStore: SourceConfigStore,
    private val notificationUtils: NotificationUtils,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun refresh() {
        Timber.d("refreshing feeds")
        withContext(defaultDispatcher) {
            repository.refreshSources(sourceConfigStore.getData()) { request, newResult, cacheResult ->
                if (newResult.first().createdAt > cacheResult.firstOrNull()?.createdAt ?: 0) {
                    Timber.d("New item in ${request.type}")
                    newResult.first().let { item ->
                        val title = item.title
                        val description = item.description ?: ""
                        notificationUtils.sendNotification(
                            title,
                            description,
                            R.drawable.ic_notification
                        )
                    }
                }
            }
        }
    }

}