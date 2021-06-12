package me.arunsharma.devupdates.workers

import com.dev.core.di.annotations.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.data.SourceConfigStore
import me.arunsharma.devupdates.data.repo.RepoFeed
import timber.log.Timber
import javax.inject.Inject

/**
 * Forces a refresh in the data repository.
 */
open class RefreshFeedSources @Inject constructor(
    private val repository: RepoFeed,
    private val sourceConfigStore: SourceConfigStore,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun refresh() {
        Timber.d("refreshing feeds")
        withContext(defaultDispatcher) {
            repository.refreshSources(sourceConfigStore.getData())
        }
    }

}