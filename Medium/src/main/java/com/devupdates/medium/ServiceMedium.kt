package com.devupdates.medium

import com.dev.services.models.DataSource
import com.devupdates.medium.models.MediumRequest
import com.devupdates.medium.models.MediumResponse
import retrofit2.Response
import retrofit2.http.*

interface ServiceMedium {

//    @POST("/{$USERNAME}/load-more?sortBy=latest")
    @POST("https://run.mocky.io/v3/23e9a4a1-daf6-46b3-a154-fa02b408ab1d")
    @Headers("accept: application/json", "x-xsrf-token: 1")
    suspend fun getFeed(
//        @Path(USERNAME) username: String,
    @Query(USERNAME) username: String,
    @Body request: MediumRequest
    ): Response<MediumResponse>

    companion object {
        private const val USERNAME = "username"
        const val ENDPOINT = "https://medium.com/"
        const val SERVICE_KEY = "MEDIUM"
    }
}