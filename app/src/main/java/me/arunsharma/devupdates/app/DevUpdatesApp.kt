package me.arunsharma.devupdates.app

import android.app.Application
import android.os.StrictMode
import androidx.work.Configuration
import com.dev.core.extensions.d
//import com.dev.devik.DevikContext
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class DevUpdatesApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    // Setup custom configuration for WorkManager with a DelegatingWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }

    override fun onCreate() {
        super.onCreate()
        enableStrictMode()
//        DevikContext(this)
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork() // or .detectAll() for all detectable problems
                .penaltyLog()
                .penaltyDeathOnNetwork()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    @Inject
    internal fun plantTimberTrees(tree: Timber.Tree) {
        Timber.plant(tree)
        d { "Timber Planted" }
    }
}