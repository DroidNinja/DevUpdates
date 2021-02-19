package com.devupdates.medium

import com.devupdates.medium.models.MediumRequest
import com.devupdates.medium.models.MediumResponse
import retrofit2.Response
import retrofit2.http.*

interface ServiceMedium {

    @POST("/{$USERNAME}/load-more?sortBy=latest")
    @Headers("accept: application/json", "x-xsrf-token: 1")
    suspend fun getFeed(
        @Path(USERNAME) username: String,
        @Body request: MediumRequest
    ): Response<MediumResponse>

    companion object {
        private const val USERNAME = "username"
        const val ENDPOINT = "https://medium.com/"
        const val SERVICE_KEY = "medium"
    }
}