package me.arunsharma.devupdates.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.services.models.ServiceItem

@Dao
interface FeedDao {

    @Query("SELECT * FROM serviceItem WHERE sourceType = :type AND groupId= :groupId  order by createdAt DESC")
    fun getFeedBySource(type: String, groupId: String): List<ServiceItem>

    @Query("SELECT * FROM serviceItem WHERE isBookmarked = 1 order by createdAt")
    fun getBookmarks(): List<ServiceItem>

    @Query("UPDATE serviceItem SET isBookmarked = :isBookmarked WHERE actionUrl = :actionUrl")
    fun setBookmark(isBookmarked: Boolean, actionUrl: String)

    @Insert(onConflict = OnConflictStrategy. REPLACE)
    fun insertAll(users: List<ServiceItem>)
}