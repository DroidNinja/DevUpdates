package com.devupdates.medium

import android.graphics.Color
import android.graphics.Color.RED
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.dev.services.models.ServiceItem
import com.dev.services.repo.ServiceIntegration
import com.devupdates.medium.models.MediumRequest
import javax.inject.Inject

class APIMedium @Inject constructor(val service: ServiceMedium) : ServiceIntegration {

    override suspend fun getData(): List<ServiceItem> {
        val username = "androiddevelopers"
        val mediumRequest = MediumRequest(25, System.currentTimeMillis())
        val response = service.getFeed(username, mediumRequest)
        if (response.isSuccessful) {
            return response.body()?.payload?.values?.map { item ->
                ServiceItem(
                    title = item.title,
                    description = item.getDescription(),
                    author = response.body()?.payload?.getAuthorName(item.creatorId),
                    likes = "♥︎ " + item.virtuals.totalClapCount,
                    actionUrl = ServiceMedium.ENDPOINT + username + "/" + item.uniqueSlug
                )
            } ?: mutableListOf()
        } else {
            return mutableListOf()
        }
    }
}