package me.arunsharma.devupdates.workers

import androidx.work.DelegatingWorkerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevUpdatesWorkerFactory @Inject constructor(
    refreshFeedSources: RefreshFeedSources
) : DelegatingWorkerFactory() {
    init {
        addFactory(RefreshWorkerFactory(refreshFeedSources))
    }
}