package me.arunsharma.devupdates.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.core.extensions.addFragment
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.ActivityMainBinding
import me.arunsharma.devupdates.helpers.deeplink.DeepLinkHandler
import me.arunsharma.devupdates.navigator.MainNavigator
import me.arunsharma.devupdates.ui.fragments.home.HomeFragment
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
