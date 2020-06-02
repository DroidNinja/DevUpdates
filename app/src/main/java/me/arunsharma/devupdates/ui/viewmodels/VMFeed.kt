package me.arunsharma.devupdates.ui.viewmodels

import com.dev.core.base.BaseViewModel
import com.dev.core.extensions.d
import javax.inject.Inject

class VMFeed @Inject constructor() : BaseViewModel() {

    fun getData() {
        d { "getData" }
    }
}