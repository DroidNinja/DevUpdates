package me.arunsharma.devupdates.ui.fragments.feed

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.utils.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentFeedBinding
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.viewmodels.VMFeed
import javax.inject.Inject


class FeedFragment : BaseFragment(R.layout.fragment_feed), DaggerInjectable {

    private val binding by viewBinding(FragmentFeedBinding::bind)

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    val viewModel: VMFeed by viewModels {
        factory
    }

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

    override fun injectDagger() {
        (activity as MainActivity).mainComponent?.inject(this)
    }

    companion object {
        const val TAG = "FeedFragment"
        fun newInstance(): FeedFragment = FeedFragment()
    }
}
