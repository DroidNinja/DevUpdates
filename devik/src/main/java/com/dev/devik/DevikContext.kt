package com.dev.devik

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import leakcanary.LeakCanary


class DevikContext(context: Context) {

    init {
        Stetho.initializeWithDefaults(context)
        SoLoader.init(context, false)

        if (FlipperUtils.shouldEnableFlipper(context)) {
            val client: FlipperClient = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(NetworkFlipperPlugin())
            client.start()
        }
    }

    fun disableLeakCanary(isEnable: Boolean = false) {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = isEnable)
        LeakCanary.showLeakDisplayActivityLauncherIcon(isEnable)
    }
}