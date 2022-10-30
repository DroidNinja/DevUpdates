package me.arunsharma.devupdates.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import com.dev.core.extensions.addFragment
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.ActivityMainBinding
import me.arunsharma.devupdates.helpers.deeplink.DeepLinkHandler
import me.arunsharma.devupdates.navigator.MainNavigator
import me.arunsharma.devupdates.ui.fragments.home.HomeFragment
import me.arunsharma.devupdates.utils.SnackbarUtil
import me.arunsharma.devupdates.workers.RefreshSourcesWorker
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityListener {

    @Inject
    lateinit var mainNavigator: MainNavigator

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        DeepLinkHandler.handleDeepLink(this, intent?.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addFragment(HomeFragment.newInstance(), R.id.fragment_container, false)
        }

        RefreshSourcesWorker.scheduleFetchEventData(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkForNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkForNotificationPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (!granted) {
                    SnackbarUtil.showBarLongTime(
                        binding.root,
                        getString(R.string.error_permission_notification),
                        SnackbarUtil.INFO
                    )
                }
            }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onResume() {
        super.onResume()
        DeepLinkHandler.handleDeepLink(this, intent?.data)
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
}
