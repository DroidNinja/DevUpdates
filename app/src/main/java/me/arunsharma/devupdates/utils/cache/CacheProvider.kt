package me.arunsharma.devupdates.utils.cache

import android.content.Context
import com.dev.core.di.annotations.ApplicationContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import javax.inject.Inject

enum class CachePolicy {
    CACHE_FIRST, //read from cache first, if not available, read from network
    NETWORK_FIRST //read from network first, save it in cache
}

data class AppCache(
    var fileName: String,
    var expiration: Int = CacheConstants.DEFAULT_CACHE_EXPIRY,
    var policy: CachePolicy = CachePolicy.CACHE_FIRST
) {
    fun getCacheName(): String {
        return fileName
    }

    /**
     * Check if cache is expired
     */
    fun checkIfCacheExpired(context: Context): Boolean {
        if(expiration > 0) {
            val lastUpdated = File(context.cacheDir, getCacheName()).lastModified()
            return (System.currentTimeMillis() - lastUpdated) >= expiration
        }
        return false
    }
}

class CachingProvider @Inject constructor(@ApplicationContext val context: Context) {

    /**
     * @param appCache Provide cache config - expiration, fileName and policy
     * @param networkCall callback for doing actual network call
     */
    inline fun <reified T> cacheData(
        appCache: AppCache,
        networkCall: () -> T
    ): T {
        when (appCache.policy) {
            CachePolicy.NETWORK_FIRST -> {
                return doNetworkCall(appCache, networkCall)
            }
            CachePolicy.CACHE_FIRST -> {
                if (!appCache.checkIfCacheExpired(context)) {
                    val cacheResult = readCache<T>(context, appCache.getCacheName())
                        ?: return doNetworkCall(appCache, networkCall)
                    return cacheResult
                } else {
                    return doNetworkCall(appCache, networkCall)
                }
            }
        }
    }

    inline fun <reified T> writeCacheData(
        appCache: AppCache,
        result: T
    ): T {
        if (result != null) {
            writeCache(context, appCache.getCacheName(), result)
        }
        return result
    }

    inline fun <reified T> doNetworkCall(
        appCache: AppCache,
        networkCall: () -> T
    ): T {
        val result = networkCall.invoke()
        if (result != null) {
            writeCache(context, appCache.getCacheName(), result)
        }
        return result
    }

    inline fun <reified T> readCache(context: Context, fileName: String): T? {
        try {
            val file = File(context.cacheDir, fileName)
            if (file.exists()) {
                val fr = FileReader(file.absoluteFile)
                val json = BufferedReader(fr).readLine()
                fr.close()
                if (!json.isNullOrEmpty()) {
                    val cacheEntryType = object : TypeToken<T>() {}.type
                    return Gson().fromJson(json, cacheEntryType)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> writeCache(context: Context, fileName: String, data: T) {
        try {
            val json = Gson().toJson(data)
            val file = File(context.cacheDir, fileName)
            val fw = FileWriter(file.absoluteFile)
            val bw = BufferedWriter(fw)
            bw.write(json)
            bw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}