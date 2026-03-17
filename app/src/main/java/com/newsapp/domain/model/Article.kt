package com.newsapp.domain.model

data class Article(
    val url: String,
    val sourceId: String?,
    val sourceName: String?,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val category: String = "general",
    val isBookmarked: Boolean = false
)

val CATEGORIES = listOf(
    "general",
    "technology",
    "business",
    "sports",
    "entertainment",
    "health",
    "science"
)
