package me.arunsharma.devupdates.workers

import android.content.Context
import androidx.work.*
import me.arunsharma.devupdates.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RefreshSourcesWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val refreshFeedSources: RefreshFeedSources
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        Timber.d("RefreshSourcesWorker")
        refreshFeedSources.refresh()
        return Result.success()
    }

    companion object {
        const val TAG = "RefreshSourcesWorker"
        private const val REPEAT_INTERVAL = 1L //in hours
        private const val REFRESH_WORKER = "REFRESH_WORKER"

        fun scheduleFetchEventData(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            if (BuildConfig.DEBUG) {
                val refreshWorker = OneTimeWorkRequestBuilder<RefreshSourcesWorker>()
                    .setConstraints(constraints)
                    .build()

                val operation = WorkManager.getInstance(context)
                    .enqueueUniqueWork(
                        REFRESH_WORKER,
                        ExistingWorkPolicy.KEEP,
                        refreshWorker
                    )
                    .result

                operation.addListener(
                    { Timber.i("refreshWorker enqueued..") },
                    { it.run() }
                )
            } else {
                val refreshWorker = PeriodicWorkRequestBuilder<RefreshSourcesWorker>(
                    REPEAT_INTERVAL,
                    TimeUnit.HOURS
                ).setConstraints(constraints)
                    .addTag(TAG)
                    .build()

                val operation = WorkManager.getInstance(context)
                    .enqueueUniquePeriodicWork(
                        REFRESH_WORKER,
                        ExistingPeriodicWorkPolicy.KEEP,
                        refreshWorker
                    )
                    .result

                operation.addListener(
                    { Timber.i("refreshWorker enqueued..") },
                    { it.run() }
                )
            }
        }
    }
}