package me.arunsharma.devupdates.compose

sealed class Screen(val route: String) {
    object Feed : Screen("feed")
    object Bookmarks : Screen("bookmarks")
    object Settings : Screen("settings")
}