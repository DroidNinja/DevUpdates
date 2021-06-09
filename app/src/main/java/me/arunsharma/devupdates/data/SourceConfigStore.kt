package me.arunsharma.devupdates.data

import android.content.Context
import com.dev.core.utils.StorageUtils
import com.dev.services.models.ServiceRequest
import com.dev.services.models.SourceConfig
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.utils.cache.AppCache
import me.arunsharma.devupdates.utils.cache.CacheConstants
import me.arunsharma.devupdates.utils.cache.CachingProvider
import javax.inject.Inject

interface SourceConfigStore {
    suspend fun getData(): List<ServiceRequest>
    suspend fun save(data: List<ServiceRequest>)
    suspend fun addSource(serviceRequest: ServiceRequest)
}

class SourceConfigStoreImpl @Inject constructor(
    @ApplicationContext val context: Context,
    val cachingProvider: CachingProvider,
    val moshi: Moshi
) : SourceConfigStore{

    val appCache = AppCache(CacheConstants.CACHE_DATASOURCES)

    override suspend fun getData(): List<ServiceRequest> {
        val config = cachingProvider.cacheData(appCache) {
            val result = StorageUtils.getRawData(context, R.raw.sources)
            val jsonAdapter = moshi.adapter(SourceConfig::class.java)
            jsonAdapter.fromJson(result)!!
        }

        return config.data
    }

    override suspend fun save(data: List<ServiceRequest>) {
        cachingProvider.writeCacheData(appCache, SourceConfig(data = data))
    }

    override suspend fun addSource(serviceRequest: ServiceRequest) {

    }


}