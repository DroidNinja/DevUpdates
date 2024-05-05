package me.arunsharma.devupdates.data

import android.content.Context
import com.dev.core.AppConstants
import com.dev.core.utils.StorageUtils
import com.dev.services.api.models.ServiceRequest
import com.dev.services.api.models.SourceConfig
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.di.data.ServiceConfig
import me.arunsharma.devupdates.utils.cache.AppCache
import me.arunsharma.devupdates.utils.cache.CacheConstants
import me.arunsharma.devupdates.utils.cache.CachingProvider
import javax.inject.Inject

interface SourceConfigStore {
    suspend fun fetchFromRemote(): List<ServiceRequest>
    suspend fun save(data: List<ServiceRequest>)
    suspend fun get(): MutableList<ServiceRequest>
    suspend fun addSource(serviceRequest: ServiceRequest)
}

class SourceConfigStoreImpl @Inject constructor(
    @ApplicationContext val context: Context,
    val cachingProvider: CachingProvider,
    val moshi: Moshi,
    val serviceConfig: ServiceConfig
) : SourceConfigStore {

    val appCache = AppCache(CacheConstants.CACHE_DATASOURCES)

    override suspend fun fetchFromRemote(): List<ServiceRequest> = withContext(Dispatchers.IO) {
        val config = cachingProvider.cacheData(appCache) {
            try {
                val result = serviceConfig.getConfig(AppConstants.CONFIG_URL)
                result
            } catch (exception: Exception) {
                val result = StorageUtils.getRawData(context, R.raw.sources)
                val jsonAdapter = moshi.adapter(SourceConfig::class.java)
                jsonAdapter.fromJson(result)
            }
        }

        return@withContext config?.data ?: mutableListOf()
    }

    override suspend fun save(data: List<ServiceRequest>) {
        withContext(Dispatchers.IO) {
            cachingProvider.writeCacheData(appCache, SourceConfig(data = data))
        }
    }

    override suspend fun get(): MutableList<ServiceRequest> = withContext(Dispatchers.IO) {
        return@withContext cachingProvider.readCacheData<SourceConfig>(appCache)?.data?.toMutableList()
            ?: mutableListOf()
    }

    override suspend fun addSource(serviceRequest: ServiceRequest) {
        withContext(Dispatchers.IO) {
            val currentItems = get()
            currentItems.add(serviceRequest)
            save(currentItems)
        }
    }
}