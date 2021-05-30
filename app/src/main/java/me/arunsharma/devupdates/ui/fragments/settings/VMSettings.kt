package me.arunsharma.devupdates.ui.fragments.settings

import android.os.Build
import com.dev.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.arunsharma.devupdates.prefs.AppPrefs
import me.arunsharma.devupdates.utils.ThemeSetter
import javax.inject.Inject

@HiltViewModel
class VMSettings @Inject constructor(val prefs: AppPrefs) : BaseViewModel() {

    fun getAvailableThemes(): List<Theme> = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            listOf(Theme.LIGHT, Theme.DARK, Theme.SYSTEM)
        }
        else -> {
            listOf(Theme.LIGHT, Theme.DARK, Theme.BATTERY_SAVER)
        }
    }

    fun setTheme(theme: Theme) {
        prefs.storeString(AppPrefs.KEY_THEME, theme.storageKey)
        ThemeSetter.setAppTheme(theme)
    }

    fun getTheme(): Theme? {
        val themeKey = prefs.getString(AppPrefs.KEY_THEME)
        return Theme.values().firstOrNull { it.storageKey == themeKey }
    }
}