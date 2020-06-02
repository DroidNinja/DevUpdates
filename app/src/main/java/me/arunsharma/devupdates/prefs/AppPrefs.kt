package me.arunsharma.devupdates.prefs

import android.content.SharedPreferences
import javax.inject.Inject

interface BasePrefs

class AppPrefs @Inject constructor(
    val sharedPreferences: SharedPreferences
) : BasePrefs {

    fun storeBoolean(key: String, data: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, data)
            apply()
        }
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }
}