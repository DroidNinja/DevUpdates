package me.arunsharma.devupdates.navigator

import androidx.fragment.app.FragmentActivity
import com.dev.core.base.BaseFragment
import com.dev.core.extensions.replaceFragment
import me.arunsharma.devupdates.R
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor() : MainNavigator {

    override fun openAddDataSourceFragment(activity: FragmentActivity?, fragment: BaseFragment) {
        activity?.replaceFragment(fragment, R.id.fragment_container, true)
    }
}