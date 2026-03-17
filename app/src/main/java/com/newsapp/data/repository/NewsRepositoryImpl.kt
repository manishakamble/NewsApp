package com.newsapp.data.repository

import com.newsapp.BuildConfig
import com.newsapp.data.local.dao.ArticleDao
import com.newsapp.data.remote.api.NewsApiService
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.NewsRepository
import com.newsapp.util.NetworkUtils
import com.newsapp.util.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

// Cache expires after 30 minutes
private const val CACHE_EXPIRY_MS = 30 * 60 * 1000L

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao,
    private val networkUtils: NetworkUtils
) : NewsRepository {

    /**
     * Offline-first strategy:
     * 1. Emit cached data immediately (if any)
     * 2. If online and (cache empty or forceRefresh), fetch from network
     * 3. Save to DB and emit fresh data
     * 4. On error, keep emitting cached data
     */
    override fun getTopHeadlines(
        category: String,
        forceRefresh: Boolean
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)

        // Step 1: Emit cached data immediately
        val cached = articleDao.getArticlesByCategory(category).first()
        if (cached.isNotEmpty()) {
            emit(Resource.Success(cached.map { it.toDomain() }))
        }

        // Step 2: Decide whether to fetch from network
        val shouldFetch = forceRefresh ||
                cached.isEmpty() ||
                isCacheExpired(cached.firstOrNull()?.cachedAt)

        if (shouldFetch && networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.getTopHeadlines(
                    category = category,
                    apiKey = BuildConfig.NEWS_API_KEY
                )

                if (response.status == "ok") {
                    // Clear stale non-bookmarked articles for this category
                    articleDao.deleteNonBookmarkedByCategory(category)

                    // Map and insert new articles
                    val entities = response.articles
                        .mapNotNull { it.toEntity(category) }
                    articleDao.insertArticles(entities)

                    // Step 3: Emit fresh data
                    emit(Resource.Success(entities.map { it.toDomain() }))
                } else {
                    if (cached.isEmpty()) {
                        emit(Resource.Error("API Error: ${response.status}"))
                    }
                }
            } catch (e: Exception) {
                // Step 4: Network error - serve from cache
                if (cached.isEmpty()) {
                    emit(Resource.Error(e.localizedMessage ?: "Unknown network error", e))
                }
                // else we already emitted cached data above
            }
        } else if (!networkUtils.isNetworkAvailable() && cached.isEmpty()) {
            emit(Resource.Error("No internet connection and no cached data available."))
        }
    }

    override fun searchNews(query: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)

        // Search local DB first
        val localResults = articleDao.searchArticles(query).first()
        if (localResults.isNotEmpty()) {
            emit(Resource.Success(localResults.map { it.toDomain() }))
        }

        // Search online if available
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.searchNews(
                    query = query,
                    apiKey = BuildConfig.NEWS_API_KEY
                )
                if (response.status == "ok") {
                    val entities = response.articles
                        .mapNotNull { it.toEntity("search") }
                    articleDao.insertArticles(entities)
                    emit(Resource.Success(entities.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                if (localResults.isEmpty()) {
                    emit(Resource.Error(e.localizedMessage ?: "Search failed"))
                }
            }
        }
    }

    override fun getBookmarkedArticles(): Flow<List<Article>> =
        articleDao.getBookmarkedArticles().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getArticleByUrl(url: String): Article? =
        articleDao.getArticleByUrl(url)?.toDomain()

    override suspend fun toggleBookmark(url: String, isBookmarked: Boolean) {
        articleDao.updateBookmarkStatus(url, isBookmarked)
    }

    override suspend fun syncNews(category: String) {
        if (!networkUtils.isNetworkAvailable()) return
        try {
            val response = apiService.getTopHeadlines(
                category = category,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            if (response.status == "ok") {
                articleDao.deleteNonBookmarkedByCategory(category)
                val entities = response.articles.mapNotNull { it.toEntity(category) }
                articleDao.insertArticles(entities)
            }
        } catch (_: Exception) { /* Sync failed silently */ }
    }

    private fun isCacheExpired(cachedAt: Long?): Boolean {
        if (cachedAt == null) return true
        return System.currentTimeMillis() - cachedAt > CACHE_EXPIRY_MS
    }
}
