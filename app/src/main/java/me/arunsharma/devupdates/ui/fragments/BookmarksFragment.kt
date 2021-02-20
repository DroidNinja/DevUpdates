package me.arunsharma.devupdates.ui.fragments

import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import me.arunsharma.devupdates.R


class BookmarksFragment : BaseFragment(R.layout.fragment_bookmarks), DaggerInjectable {
    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {

    }

    override fun injectDagger() {

    }

    companion object {
        const val TAG = "BookmarksFragment"
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }
}
