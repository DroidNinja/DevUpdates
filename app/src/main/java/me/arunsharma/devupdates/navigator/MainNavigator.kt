package me.arunsharma.devupdates.navigator

import androidx.fragment.app.FragmentActivity
import com.dev.core.base.BaseFragment

interface MainNavigator {
    fun openAddDataSourceFragment(activity: FragmentActivity?, fragment: BaseFragment)
}