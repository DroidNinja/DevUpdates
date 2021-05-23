package com.dev.core.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dev.core.di.utils.DaggerInjectable


abstract class BaseFragment(@LayoutRes fragLayout: Int) : Fragment(fragLayout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(this is DaggerInjectable) {
            injectDagger()
        }

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
