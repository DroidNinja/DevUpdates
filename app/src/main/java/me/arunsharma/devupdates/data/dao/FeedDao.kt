package me.arunsharma.devupdates.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.services.models.ServiceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FeedDao {

    @Query("SELECT * FROM serviceItem WHERE sourceType = :type AND groupId= :groupId AND createdAt < :time order by createdAt DESC")
    fun getFeedBySource(type: String, groupId: String, time: Long): List<ServiceItem>

    @Query("SELECT * FROM serviceItem WHERE createdAt < :time order by createdAt DESC LIMIT 25")
    fun getAllFeed(time: Long): List<ServiceItem>

    @Query("SELECT * FROM serviceItem order by createdAt DESC")
    fun observeFeed(): Flow<List<ServiceItem>>

    @Query("SELECT * FROM serviceItem WHERE isBookmarked = 1 order by createdAt")
    fun getBookmarks(): Flow<List<ServiceItem>>

    @Query("UPDATE serviceItem SET isBookmarked = :isBookmarked WHERE actionUrl = :actionUrl")
    fun setBookmark(isBookmarked: Boolean, actionUrl: String)

    @Insert(onConflict = OnConflictStrategy. REPLACE)
    fun insertAll(users: List<ServiceItem>)
}