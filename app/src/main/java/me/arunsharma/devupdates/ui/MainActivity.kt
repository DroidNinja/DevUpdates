package me.arunsharma.devupdates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.arunsharma.devupdates.BackStackManager
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.app.DevUpdatesApp
import me.arunsharma.devupdates.databinding.ActivityMainBinding
import me.arunsharma.devupdates.di.activity.MainComponent
import me.arunsharma.devupdates.ui.fragments.BookmarksFragment
import me.arunsharma.devupdates.ui.fragments.addsource.AddDataSourceFragment
import me.arunsharma.devupdates.ui.fragments.settings.SettingsFragment
import me.arunsharma.devupdates.ui.fragments.feed.FeedFragment

class MainActivity : AppCompatActivity() {

    private lateinit var backStackManager: BackStackManager
    lateinit var mainComponent: MainComponent

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainComponent = (applicationContext as DevUpdatesApp)
            .appComponent.mainComponent().create()
        mainComponent.inject(this)
        backStackManager = BackStackManager(this, R.id.fragment_container)

        if (savedInstanceState == null) {
            backStackManager.setFragment(FeedFragment.TAG, FeedFragment.newInstance())
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    backStackManager.setFragment(
                        FeedFragment.TAG,
                        FeedFragment.newInstance()
                    )
                    true
                }
                R.id.bookmarks -> {
                    backStackManager.setFragment(
                        BookmarksFragment.TAG,
                        BookmarksFragment.newInstance()
                    )
                    true
                }
                R.id.settings -> {
                    backStackManager.setFragment(
                        AddDataSourceFragment.TAG,
                        AddDataSourceFragment.newInstance()
                    )
                    true
                }
                else -> false
            }
        }

        backStackManager.setUpWithBottomNavigation {
            when (it) {
                is FeedFragment -> {
                    binding.bottomNavigation.menu.getItem(FEED_FRAGMENT).isChecked = true
                }
                is BookmarksFragment -> {
                    binding.bottomNavigation.menu.getItem(BOOKMARKS_FRAGMENT).isChecked = true
                }
                else -> {
                    binding.bottomNavigation.menu.getItem(SETTINGS_FRAGMENT).isChecked = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backStackManager.onBackPressed()) {
            finish()
        }
        else {
            super.onBackPressed()
        }
    }

    companion object {
        const val FEED_FRAGMENT = 0
        const val BOOKMARKS_FRAGMENT = 1
        const val SETTINGS_FRAGMENT = 2
    }
}
