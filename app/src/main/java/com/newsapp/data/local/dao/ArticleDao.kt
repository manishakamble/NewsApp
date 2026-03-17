package com.newsapp.data.local.dao

import androidx.room.*
import com.newsapp.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    // ─── Insert / Upsert ────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    // ─── Queries ─────────────────────────────────────────────────────────────────

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY publishedAt DESC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isBookmarked = 1 ORDER BY publishedAt DESC")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    @Query("""
        SELECT * FROM articles 
        WHERE title LIKE '%' || :query || '%' 
           OR description LIKE '%' || :query || '%'
        ORDER BY publishedAt DESC
    """)
    fun searchArticles(query: String): Flow<List<ArticleEntity>>

    // ─── Update ──────────────────────────────────────────────────────────────────

    @Query("UPDATE articles SET isBookmarked = :isBookmarked WHERE url = :url")
    suspend fun updateBookmarkStatus(url: String, isBookmarked: Boolean)

    // ─── Delete ──────────────────────────────────────────────────────────────────

    @Query("DELETE FROM articles WHERE category = :category AND isBookmarked = 0")
    suspend fun deleteNonBookmarkedByCategory(category: String)

    @Query("DELETE FROM articles WHERE cachedAt < :expiryTime AND isBookmarked = 0")
    suspend fun deleteExpiredArticles(expiryTime: Long)

    // ─── Count ───────────────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM articles WHERE category = :category")
    suspend fun getArticleCountByCategory(category: String): Int
}
