package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(category: String, forceRefresh: Boolean = false): Flow<Resource<List<Article>>>
    fun searchNews(query: String): Flow<Resource<List<Article>>>
    fun getBookmarkedArticles(): Flow<List<Article>>
    suspend fun getArticleByUrl(url: String): Article?
    suspend fun toggleBookmark(url: String, isBookmarked: Boolean)
    suspend fun syncNews(category: String)
}
