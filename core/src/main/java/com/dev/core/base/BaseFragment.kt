package com.dev.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


abstract class BaseFragment(@LayoutRes fragLayout: Int) : Fragment(fragLayout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.attributes?.softInputMode?.let {
            activity?.window?.setSoftInputMode(it)
        }
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    abstract fun getFragmentTag(): String

    abstract fun initView()
}
