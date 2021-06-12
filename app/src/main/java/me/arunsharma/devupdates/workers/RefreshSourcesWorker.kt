package me.arunsharma.devupdates.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber

class RefreshSourcesWorker(appContext: Context,
                           workerParams: WorkerParameters,
                          val  refreshFeedSources: RefreshFeedSources) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        Timber.d("RefreshSourcesWorker")
        refreshFeedSources.refresh()
        return Result.success()
    }

    companion object {
        const val TAG = "RefreshSourcesWorker"
        const val REPEAT_INTERVAL = 1L //in hours
    }
}