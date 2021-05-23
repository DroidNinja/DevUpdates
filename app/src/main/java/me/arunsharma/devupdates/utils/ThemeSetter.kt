package me.arunsharma.devupdates.utils

import androidx.appcompat.app.AppCompatDelegate
import me.arunsharma.devupdates.ui.fragments.settings.Theme

object ThemeSetter {
    fun setAppTheme( value: Theme) {
        when (value) {
            Theme.LIGHT-> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Theme.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Theme.SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            Theme.BATTERY_SAVER -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }
    }
}