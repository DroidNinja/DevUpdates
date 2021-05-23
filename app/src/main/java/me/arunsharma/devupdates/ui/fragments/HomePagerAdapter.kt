package me.arunsharma.devupdates.ui.fragments;

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dev.core.base.BaseFragment

class HomePagerAdapter(fa: FragmentManager, val feedItems: MutableList<BaseFragment>) :
    FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return feedItems.size
    }

    override fun getItem(position: Int): Fragment {
        return feedItems[position]
    }
}