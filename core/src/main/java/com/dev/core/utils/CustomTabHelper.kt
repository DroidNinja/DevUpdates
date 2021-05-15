package com.dev.core.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import com.dev.core.R
import com.dev.core.extensions.e

object CustomTabHelper {
    private fun getDefaultBrowser(context: Context): String? {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
        val packageManager = context.packageManager
        val resolveInfo =
            packageManager.resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
        resolveInfo ?: return null
        return resolveInfo.activityInfo.packageName
    }

    private fun getCustomTabsPackages(context: Context): List<String> {
        val packageManager = context.packageManager
        val activityIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
        val resolvedActivityList =
            packageManager.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs = mutableListOf<String>()
        resolvedActivityList.forEach {
            val serviceIntent = Intent()
            serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(it.activityInfo.packageName)
            if (packageManager.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(it.activityInfo.packageName)
            }
        }
        return packagesSupportingCustomTabs
    }

    private fun getPreferredCustomTabsPackage(context: Context): String? {
        val defaultBrowser = getDefaultBrowser(context)
        val supportedPackages = getCustomTabsPackages(context)
        if (supportedPackages.isEmpty()) return null
        val preferredPackage = getCustomTabsPackages(context)
            .firstOrNull { it == defaultBrowser }
        return preferredPackage ?: supportedPackages[0]
    }

    private fun getCustomTabIntent(context: Context): CustomTabsIntent? {
        val customTabsBuilder = CustomTabsIntent.Builder()
        customTabsBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
        customTabsBuilder.setExitAnimations(
            context, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        val colorSchemeBuilder = CustomTabColorSchemeParams.Builder().apply {
            setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            setNavigationBarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.white))
        }
        customTabsBuilder.setDefaultColorSchemeParams(
            colorSchemeBuilder.build()
        )
        val customTabsIntent = customTabsBuilder.build()
        val preferredPackage = getPreferredCustomTabsPackage(context) ?: return null
        customTabsIntent.intent.setPackage(preferredPackage)
        return customTabsIntent
    }

    fun open(context: Context, url: String?) {
        try {
            val intent = getCustomTabIntent(context)
            intent?.launchUrl(context, Uri.parse(url))
        } catch (exception: Exception) {
            e { exception.toString() }
        }
    }
}