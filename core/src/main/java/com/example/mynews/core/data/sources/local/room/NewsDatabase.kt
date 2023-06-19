package com.example.mynews.core.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynews.core.data.sources.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}