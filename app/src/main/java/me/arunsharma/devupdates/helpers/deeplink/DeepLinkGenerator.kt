package me.arunsharma.devupdates.helpers.deeplink

import android.net.Uri

enum class ScreenType(val type: String) {
    HOME("home"),
    BOOKMARK("bookmark"),
    SETTING("setting"),
    OPEN_URL("openurl")
}

sealed class Screen(val type: ScreenType) {
    data class Home(val position: Int = 0) : Screen(ScreenType.HOME) {
        override fun generateDeepLinkBuilder(): Uri.Builder {
            return super.generateDeepLinkBuilder()
                .appendQueryParameter(DeepLinkGenerator.QUERY_POSITION, position.toString())
        }
    }

    object Bookmark : Screen(ScreenType.BOOKMARK)
    object Setting : Screen(ScreenType.SETTING)

    data class OpenUrl(val url: String) : Screen(ScreenType.OPEN_URL) {
        override fun generateDeepLinkBuilder(): Uri.Builder {
            return super.generateDeepLinkBuilder()
                .appendQueryParameter(DeepLinkGenerator.QUERY_URL, url)
        }
    }

    open fun generateDeepLinkBuilder(): Uri.Builder {
        return DeepLinkGenerator.getAppUriBuilder()
            .appendQueryParameter(DeepLinkGenerator.QUERY_PAGE, type.type)
    }
}


class DeepLinkGenerator {
    companion object {
        const val SCHEME = "devupdates"
        const val HOST = "app"
        const val QUERY_PAGE = "page"
        const val QUERY_POSITION = "position"
        const val QUERY_URL = "url"

        fun getAppUriBuilder(): Uri.Builder {
            return Uri.Builder()
                .scheme(SCHEME)
                .authority(HOST)
        }

        fun generateDeeplink(screen: Screen): Uri? {
            return screen.generateDeepLinkBuilder().build()
        }
    }
}