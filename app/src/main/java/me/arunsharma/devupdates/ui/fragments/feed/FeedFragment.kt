package me.arunsharma.devupdates.ui.fragments.feed

import androidx.fragment.app.viewModels
import com.dev.core.base.BaseFragment
import com.dev.core.utils.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentFeedBinding
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerAdapter
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeed
import me.arunsharma.devupdates.utils.EventBus
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : BaseFragment(R.layout.fragment_feed) {

    private val binding by viewBinding(FragmentFeedBinding::bind)

    val viewModel: VMFeed by viewModels()

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        binding.viewPager.offscreenPageLimit = 2
        viewModel.lvFetchConfig.observe(viewLifecycleOwner, {
            setUpViewPager(it)
        })

        if(viewModel.lvFetchConfig.value == null) {
            viewModel.getConfig()
        }
    }

    private fun setUpViewPager(items: List<FeedPagerItem>) {
        val adapter = FeedPagerAdapter(
            requireActivity(), items
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = adapter.getTabView(position)
        }.attach()
    }

    companion object {
        const val TAG = "FeedFragment"
        fun newInstance(): FeedFragment = FeedFragment()
    }
}
