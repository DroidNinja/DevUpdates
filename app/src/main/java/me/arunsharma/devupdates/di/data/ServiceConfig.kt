package me.arunsharma.devupdates.di.data

import com.dev.services.models.SourceConfig
import retrofit2.http.GET
import retrofit2.http.Url

interface ServiceConfig {
    @GET
    suspend fun getConfig(
        @Url url: String
    ): SourceConfig

    companion object {
        const val ENDPOINT = "https://github.com/"
        const val SERVICE_KEY = "CONFIG"
    }
}