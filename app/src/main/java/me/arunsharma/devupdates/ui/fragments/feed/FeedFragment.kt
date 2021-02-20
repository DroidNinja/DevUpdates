package me.arunsharma.devupdates.ui.fragments.feed

import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.utils.viewBinding
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import com.devupdates.medium.ServiceMediumRequest
import com.google.android.material.tabs.TabLayoutMediator
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentFeedBinding


class FeedFragment : BaseFragment(R.layout.fragment_feed), DaggerInjectable {

    private val binding by viewBinding(FragmentFeedBinding::bind)

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        val adapter = FeedPagerAdapter(
            requireActivity(), mutableListOf(
                FeedPagerItem(
                    "Trending",
                    R.drawable.ic_github,
                    FeedListFragment.newInstance(ServiceRequest(DataSource.GITHUB))
                ),
                FeedPagerItem(
                    "Android Developers",
                    R.drawable.ic_logo_medium,
                    FeedListFragment.newInstance(
                        ServiceMediumRequest(
                            DataSource.MEDIUM,
                            "androiddevelopers"
                        )
                    )
                ),
                FeedPagerItem(
                    "ProAndroidDev",
                    R.drawable.ic_logo_medium,
                    FeedListFragment.newInstance(
                        ServiceMediumRequest(
                            DataSource.MEDIUM,
                            "proandroiddev"
                        )
                    )
                )
            )
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setCustomView(adapter.getTabView(position))
        }.attach()
    }

    override fun injectDagger() {

    }

    companion object {
        const val TAG = "FeedFragment"
        fun newInstance(): FeedFragment = FeedFragment()
    }
}
