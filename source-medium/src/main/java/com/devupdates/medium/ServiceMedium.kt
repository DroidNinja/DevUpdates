package com.devupdates.medium

import com.devupdates.medium.models.MediumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ServiceMedium {

    @GET("{$USERNAME}/load-more")
    @Headers("accept: application/json", "x-xsrf-token: 1")
    suspend fun getFeed(
        @Path(USERNAME) username: String,
    @QueryMap request: MutableMap<String, String?>
    ): Response<MediumResponse>


    @GET("{$USERNAME}/load-more")
    @Headers("accept: application/json", "x-xsrf-token: 1")
    suspend fun getTaggedFeed(
        @Path(USERNAME) username: String,
        @QueryMap request: MutableMap<String, String?>
    ): Response<MediumResponse>

    companion object {
        private const val USERNAME = "username"
        const val ENDPOINT = "https://medium.com/"
        const val SERVICE_KEY = "MEDIUM"
    }
}