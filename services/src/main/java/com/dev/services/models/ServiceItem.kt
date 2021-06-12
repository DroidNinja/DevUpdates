package com.dev.services.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ServiceItem(
    @PrimaryKey
    var actionUrl: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "author")
    var author: String? = null,
    @ColumnInfo(name = "likes")
    var likes: String?,
    @ColumnInfo(name = "createdAt")
    var createdAt: Long = 0L,
    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false,
    @ColumnInfo(name = "sourceType")
    var sourceType: String?,
    @ColumnInfo(name = "groupId")
    var groupId: String?,
    @ColumnInfo(name = "topTitleText")
    var topTitleText: String?,
)