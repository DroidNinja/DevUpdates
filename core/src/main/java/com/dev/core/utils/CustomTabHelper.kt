package com.dev.core.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object CustomTabHelper {

    fun open(context: Context, url: String?) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}