package me.arunsharma.devupdates.ui.fragments.feed

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev.core.base.BaseFragment
import me.arunsharma.devupdates.databinding.LayoutTabViewBinding

data class FeedPagerItem(
    val title: String,
    val icon: Int,
    val fragment: BaseFragment
)

class FeedPagerAdapter(val fa: FragmentActivity, val feedItems: List<FeedPagerItem>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = feedItems.size

    override fun createFragment(position: Int): Fragment = feedItems[position].fragment

    fun getTabView(position: Int): View {
        val binding = LayoutTabViewBinding.inflate(LayoutInflater.from(fa))
        binding.ivTabItem.setImageResource(feedItems[position].icon)
        binding.tvSubTitle.text = feedItems[position].title
        return binding.root
    }
}