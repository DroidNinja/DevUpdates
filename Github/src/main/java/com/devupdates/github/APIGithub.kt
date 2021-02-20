package com.devupdates.github

import android.graphics.Color
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.repo.ServiceIntegration
import javax.inject.Inject

class APIGithub @Inject constructor(val service: ServiceGithub) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): List<ServiceItem> {
        return service.getTrending("kotlin", "daily").map { item ->
            ServiceItem(
                title = item.author + " / " + item.name,
                description = item.description,
                author = buildSpannedString {
                    color(Color.parseColor(item.languageColor)) {
                        append(item.language)
                    }
                },
                likes = "★ " + item.stars?.toString(),
                actionUrl = item.url
            )
        }
    }
}