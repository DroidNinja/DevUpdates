package me.arunsharma.devupdates.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class RefreshWorkerFactory(
    private val refreshFeedSources: RefreshFeedSources
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            RefreshSourcesWorker::class.java.name ->
                RefreshSourcesWorker(appContext, workerParameters, refreshFeedSources)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}