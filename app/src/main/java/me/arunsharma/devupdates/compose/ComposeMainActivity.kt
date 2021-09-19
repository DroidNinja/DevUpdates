package me.arunsharma.devupdates.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.helpers.deeplink.DeepLinkHandler

@AndroidEntryPoint
class ComposeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevUpdatesApp()
        }

//        RefreshSourcesWorker.scheduleFetchEventData(this)
    }

    override fun onResume() {
        super.onResume()
        DeepLinkHandler.handleDeepLink(this, intent?.data)
    }
}
