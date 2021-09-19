package me.arunsharma.devupdates.compose.theme

import androidx.compose.ui.graphics.Color

fun getTabTextColorSelector(darkTheme: Boolean, selected: Boolean): Color {
    return if (darkTheme) {
        if (selected) {
            DarkThemeColor.primary
        } else {
            DarkThemeColor.primary_50
        }
    } else {
        if (selected) {
            LightThemeColor.primary
        } else {
            LightThemeColor.primary_50
        }
    }
}

fun getBottomTabColorSelector(darkTheme: Boolean, selected: Boolean): Color {
    return if (darkTheme) {
        if (selected) {
            DarkThemeColor.primary
        } else {
            DarkThemeColor.primary_50
        }
    } else {
        if (selected) {
            LightThemeColor.primary
        } else {
            LightThemeColor.primary_50
        }
    }
}