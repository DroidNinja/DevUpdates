package me.arunsharma.devupdates.helpers.deeplink

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.dev.core.utils.CustomTabHelper

class DeepLinkHandler {
    companion object {

        fun handleDeepLink(context: FragmentActivity, data: Uri?) {
            if (data != null) {
                val intentParams = getQueryMap(data.query)
                intentParams.forEach { entry ->
                    context.intent?.putExtra(entry.key, entry.value)
                }
                val isHandled = handlePage(context, data)
                if (isHandled) {
                    context.intent.data = null
                }
            }
        }

        private fun handlePage(context: FragmentActivity, uri: Uri): Boolean {
            val page = uri.getQueryParameter(DeepLinkGenerator.QUERY_PAGE)
            return when (page) {
                ScreenType.OPEN_URL.type -> {
                    CustomTabHelper.open(
                        context,
                        uri.getQueryParameter(DeepLinkGenerator.QUERY_URL)
                    )
                    true
                }
                else -> false
            }
        }

        private fun getQueryMap(query: String?): MutableMap<String, String> {
            val map = mutableMapOf<String, String>()
            val params = query?.split("&")
            params?.forEach { item ->
                val itemParam = item.split("=")
                if (itemParam.size == 2) {
                    map[itemParam[0]] = itemParam[1]
                }
            }
            return map
        }
    }
}