package com.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsapp.data.local.dao.ArticleDao
import com.newsapp.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "news_database"
    }
}
