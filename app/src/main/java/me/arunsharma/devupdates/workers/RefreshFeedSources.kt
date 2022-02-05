package me.arunsharma.devupdates.workers

import android.content.Context
import android.content.Intent
import com.dev.core.di.annotations.DefaultDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.repo.RepoFeed
import me.arunsharma.devupdates.helpers.deeplink.DeepLinkGenerator
import me.arunsharma.devupdates.helpers.deeplink.Screen
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.utils.NotificationUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * Forces a refresh in the data repository.
 */
open class RefreshFeedSources @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: RepoFeed,
    private val sourceConfigStore: SourceConfigStore,
    private val notificationUtils: NotificationUtils,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun refresh() {
        Timber.d("refreshing feeds")

        withContext(defaultDispatcher) {
            repository.refreshSources(sourceConfigStore.fetchFromRemote()) { request, newResult, cacheResult ->
                val newResultCreatedAt = newResult.first().createdAt
                val cacheResultCreatedAt = cacheResult.firstOrNull()?.createdAt ?: 0
                if ( newResultCreatedAt >  cacheResultCreatedAt) {
                    Timber.d("New item in ${request.type}==${request.getGroupId()} == $newResultCreatedAt == $cacheResultCreatedAt")
                    newResult.first().let { item ->
                        val title = item.title
                        val description = item.description ?: ""

                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        }.apply {
                            data = DeepLinkGenerator.generateDeeplink(Screen.OpenUrl(item.actionUrl))
                        }
                        notificationUtils.sendNotification(
                            title,
                            description,
                            R.drawable.ic_notification,
                            intent,
                            item.hashCode()
                        )
                    }
                }
            }
        }
    }

}