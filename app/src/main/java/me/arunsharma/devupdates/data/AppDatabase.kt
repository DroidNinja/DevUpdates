package me.arunsharma.devupdates.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.services.models.ServiceItem
import me.arunsharma.devupdates.data.dao.FeedDao

@Database(entities = [ServiceItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    companion object {
        const val DATABASE_NAME = "devupdates"
    }
}