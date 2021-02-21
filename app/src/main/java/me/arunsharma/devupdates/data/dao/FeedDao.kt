package me.arunsharma.devupdates.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dev.services.models.ServiceItem

@Dao
interface FeedDao {

    @Query("SELECT * FROM serviceItem WHERE sourceType = :type AND groupId= :groupId  order by createdAt")
    fun getFeedBySource(type: String, groupId: String): List<ServiceItem>

    @Query("SELECT * FROM serviceItem WHERE isBookmarked = 1 order by createdAt")
    fun getBookmarks(): List<ServiceItem>

    @Insert
    fun insertAll(users: List<ServiceItem>)
}