package com.devupdates.github

import com.devupdates.github.models.GithubTrendingResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceGithub {

    @GET("/trending/{$LANGUAGE}")
    suspend fun getTrending(
        @Path(LANGUAGE) language: String,
        @Query("since") since: String
    ): List<GithubTrendingResponseItem>

    companion object {
        private const val LANGUAGE = "language"
        const val SELECTED_LANGUAGE = "selectedLang"
        const val ENDPOINT = "https://github.com/"
        const val SERVICE_KEY = "GITHUB"
    }
}