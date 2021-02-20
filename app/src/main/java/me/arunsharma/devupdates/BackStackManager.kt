package me.arunsharma.devupdates

import androidx.fragment.app.FragmentActivity
import com.dev.core.base.BaseFragment
import com.dev.core.extensions.getCurrentFragment
import com.dev.core.extensions.isFragmentInBackStack
import com.dev.core.extensions.popTo
import com.dev.core.extensions.replaceFragment

class BackStackManager(val activity: FragmentActivity, val containerId: Int) {

    fun setFragment(tag: String, fragment: BaseFragment) {
        if (activity.isFragmentInBackStack(tag)) {
            activity.popTo(tag, 0)
        } else {
            activity.replaceFragment(fragment, containerId, true)
        }
    }

    fun onBackPressed(): Boolean {
        return activity.supportFragmentManager.backStackEntryCount > 1
    }

    fun setUpWithBottomNavigation(onFragmentChanged: (BaseFragment?) -> Unit) {
        activity.supportFragmentManager.addOnBackStackChangedListener {
            onFragmentChanged(activity.getCurrentFragment(containerId))
        }
    }

}