package com.devupdates.medium

import android.text.format.DateUtils
import com.dev.network.model.APIErrorException
import com.dev.network.model.ResponseStatus
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import com.dev.services.models.ServiceResult
import com.dev.services.repo.ServiceIntegration
import com.devupdates.medium.models.Collection
import com.devupdates.medium.models.MediumResponse
import javax.inject.Inject

class APIMedium @Inject constructor(val service: ServiceMedium) : ServiceIntegration {

    override suspend fun getData(request: ServiceRequest): ResponseStatus<ServiceResult> {
        val username = request.metadata?.get("username") ?: ""
        val requestMap = mutableMapOf(
            "sortBy" to "latest",
            "limit" to "10",
            "to" to request.next
        )

        return if (request.metadata?.get("tag") != null) {
            requestMap["tagSlug"] = request.metadata?.get("tag")
            requestMap["sortBy"] = "tagged"
            getTaggedFeed(username, requestMap, request.getGroupId())
        } else {
            getFeed(username, requestMap, request.getGroupId())
        }
    }

    private suspend fun getTaggedFeed(
        username: String,
        requestMap: MutableMap<String, String?>,
        groupId: String
    ): ResponseStatus<ServiceResult> {
        val response = service.getTaggedFeed(username, requestMap)
        if (response.isSuccessful) {
            return ResponseStatus.success(ServiceResult(response.body()?.payload?.getTaggedPosts()?.map { item ->
                mapCollectionToServiceItem(username, response.body(), item, groupId)
            } ?: mutableListOf()))
        } else {
            return ResponseStatus.failure(APIErrorException.httpError(response))
        }
    }

    private suspend fun getFeed(
        username: String,
        requestMap: MutableMap<String, String?>,
        groupId: String
    ): ResponseStatus<ServiceResult> {
        val response = service.getFeed(username, requestMap)
        if (response.isSuccessful) {
            return ResponseStatus.success(ServiceResult(response.body()?.payload?.values?.map { item ->
                mapCollectionToServiceItem(username, response.body(), item, groupId)
            } ?: mutableListOf()))
        } else {
            return ResponseStatus.failure(APIErrorException.httpError(response))
        }
    }

    private fun mapCollectionToServiceItem(
        username: String,
        response: MediumResponse?,
        item: Collection?,
        groupId: String
    ): ServiceItem {
        return ServiceItem(
            title = item?.title ?: "",
            description = item?.getDescription(),
            author = response?.payload?.getAuthorName(item?.creatorId ?: ""),
            topTitleText = response?.payload?.getAuthorName(item?.creatorId ?: "") + " ● " + DateUtils.getRelativeTimeSpanString(item?.createdAt ?: 0),
            likes = "♥︎ " + item?.virtuals?.totalClapCount,
            actionUrl = ServiceMedium.ENDPOINT + username + "/" + item?.uniqueSlug,
            sourceType = DataSource.MEDIUM.toString(),
            groupId = groupId,
            createdAt = item?.createdAt ?: 0
        )
    }
}