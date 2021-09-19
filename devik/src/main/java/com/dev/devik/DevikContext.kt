package com.dev.devik

import android.content.Context
import com.facebook.stetho.Stetho
import leakcanary.LeakCanary


class DevikContext(context: Context) {

    init {
        Stetho.initializeWithDefaults(context)
//        SoLoader.init(context, false)

//        if (FlipperUtils.shouldEnableFlipper(context)) {
//            val client: FlipperClient = AndroidFlipperClient.getInstance(context)
//            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
//            client.addPlugin(NetworkFlipperPlugin())
//            client.start()
//        }
    }

    fun disableLeakCanary(isEnable: Boolean = false) {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = isEnable)
        LeakCanary.showLeakDisplayActivityLauncherIcon(isEnable)
    }
}