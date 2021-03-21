package me.arunsharma.devupdates.app

import android.app.Application
import android.os.StrictMode
import com.dev.core.extensions.d
import timber.log.Timber
import javax.inject.Inject

class DevUpdatesApp : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@DevUpdatesApp)

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
                .detectLeakedClosableObjects()
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