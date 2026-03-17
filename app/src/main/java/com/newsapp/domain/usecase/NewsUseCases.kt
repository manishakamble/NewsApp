package com.newsapp.domain.usecase

import com.newsapp.domain.model.Article
import com.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(
        category: String,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<Article>>> =
        repository.getTopHeadlines(category, forceRefresh)
}

class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Article>>> =
        repository.searchNews(query)
}

class GetBookmarksUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> =
        repository.getBookmarkedArticles()
}

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(url: String, isBookmarked: Boolean) =
        repository.toggleBookmark(url, isBookmarked)
}

class GetArticleByUrlUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? =
        repository.getArticleByUrl(url)
}
