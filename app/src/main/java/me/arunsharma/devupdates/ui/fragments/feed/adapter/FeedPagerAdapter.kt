package me.arunsharma.devupdates.ui.fragments.feed.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.databinding.LayoutTabViewBinding
import me.arunsharma.devupdates.ui.fragments.feed.FeedListFragment
import me.arunsharma.devupdates.ui.fragments.feed.GithubFragment
import me.arunsharma.devupdates.ui.fragments.feed.HomeFeedFragment

data class FeedPagerItem(
    val icon: Int,
    val request: ServiceRequest
)

class FeedPagerAdapter(val fa: FragmentActivity, val feedItems: List<FeedPagerItem>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = feedItems.size

    override fun createFragment(position: Int): Fragment {
        return if (feedItems[position].request.type == DataSource.ALL) {
            HomeFeedFragment.newInstance(feedItems[position].request)
        } else if (feedItems[position].request.type == DataSource.GITHUB) {
            GithubFragment.newInstance(feedItems[position].request)
        } else {
            FeedListFragment.newInstance(feedItems[position].request)
        }
    }

    fun getTabView(position: Int): View {
        val binding = LayoutTabViewBinding.inflate(LayoutInflater.from(fa))
        binding.ivTabItem.setImageResource(feedItems[position].icon)
        binding.tvSubTitle.text = feedItems[position].request.name
        return binding.root
    }
}