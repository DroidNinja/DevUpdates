package me.arunsharma.devupdates.app

import android.app.Application
import android.os.StrictMode
import com.dev.core.extensions.d
import com.dev.devik.di.DevikContext
import timber.log.Timber
import javax.inject.Inject

class DevUpdatesApp : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .create(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@DevUpdatesApp)
        DevikContext(this)
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