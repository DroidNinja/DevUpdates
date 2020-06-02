package me.arunsharma.devupdates.app

import android.app.Application
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
    }

    @Inject
    internal fun plantTimberTrees(tree: Timber.Tree) {
        Timber.plant(tree)
        d { "Timber Planted" }
    }
}