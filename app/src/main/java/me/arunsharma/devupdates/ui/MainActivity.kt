package me.arunsharma.devupdates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.dev.core.extensions.addFragment
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.ActivityMainBinding
import me.arunsharma.devupdates.navigator.MainNavigator
import me.arunsharma.devupdates.ui.fragments.home.HomeFragment
import me.arunsharma.devupdates.workers.RefreshSourcesWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityListener {

    @Inject
    lateinit var mainNavigator: MainNavigator

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addFragment(HomeFragment.newInstance(), R.id.fragment_container, false)
        }
        scheduleFetchEventData()
    }

    private fun scheduleFetchEventData() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

//        val refreshWorker = OneTimeWorkRequestBuilder<RefreshSourcesWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        val operation = WorkManager.getInstance(this)
//            .enqueueUniqueWork(
//                REFRESH_WORKER,
//                ExistingWorkPolicy.KEEP,
//                refreshWorker
//            )
//            .result

        val refreshWorker = PeriodicWorkRequestBuilder<RefreshSourcesWorker>(
            RefreshSourcesWorker.REPEAT_INTERVAL,
            TimeUnit.HOURS
        ).setConstraints(constraints)
            .addTag(RefreshSourcesWorker.TAG)
            .build()

        val operation = WorkManager.getInstance(this)
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun getNavigator(): MainNavigator {
        return mainNavigator
    }

    companion object {
        private const val REFRESH_WORKER = "REFRESH_WORKER"
    }
}
