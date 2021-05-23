package me.arunsharma.devupdates.prefs

import android.content.SharedPreferences
import javax.inject.Inject

interface BasePrefs

class AppPrefs @Inject constructor(
    val sharedPreferences: SharedPreferences
) : BasePrefs {

    companion object {
        const val KEY_THEME = "selectedTheme"
    }

    fun storeBoolean(key: String, data: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, data)
            apply()
        }
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    fun getString(key: String, default: String = ""): String? {
        return sharedPreferences.getString(key, default)
    }

    fun storeString(key: String, data: String) {
        sharedPreferences.edit().apply {
            putString(key, data)
            apply()
        }
    }
}