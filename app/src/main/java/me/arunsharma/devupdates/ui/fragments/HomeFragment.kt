package me.arunsharma.devupdates.ui.fragments

import androidx.viewpager.widget.ViewPager
import com.dev.core.base.BaseFragment
import com.dev.core.utils.viewBinding
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentHomeBinding
import me.arunsharma.devupdates.ui.fragments.feed.FeedFragment
import me.arunsharma.devupdates.ui.fragments.settings.SettingsFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val fragments: MutableList<BaseFragment> by lazy {
        mutableListOf(
            FeedFragment.newInstance(),
            BookmarksFragment.newInstance(),
            SettingsFragment.newInstance()
        )
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        binding.viewPager.adapter = HomePagerAdapter(
            childFragmentManager, fragments
        )
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> {
                    binding.viewPager.currentItem = FEED_FRAGMENT
                    true
                }
                R.id.bookmarks -> {
                    binding.viewPager.currentItem = BOOKMARKS_FRAGMENT
                    true
                }
                R.id.settings -> {
                    binding.viewPager.currentItem = SETTINGS_FRAGMENT
                    true
                }
                else -> false
            }
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked =
                    true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    companion object {
        const val TAG = "HomeFragment"

        const val FEED_FRAGMENT = 0
        const val BOOKMARKS_FRAGMENT = 1
        const val SETTINGS_FRAGMENT = 2

        fun newInstance() = HomeFragment()
    }
}