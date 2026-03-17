package com.newsapp.data.repository

import com.newsapp.data.local.entity.ArticleEntity
import com.newsapp.data.remote.dto.ArticleDto
import com.newsapp.domain.model.Article

// ─── DTO → Entity ────────────────────────────────────────────────────────────

fun ArticleDto.toEntity(category: String = "general"): ArticleEntity? {
    val safeUrl = url ?: return null
    val safeTitle = title ?: return null
    return ArticleEntity(
        url = safeUrl,
        sourceId = source?.id,
        sourceName = source?.name,
        author = author,
        title = safeTitle,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        category = category
    )
}

// ─── Entity → Domain ─────────────────────────────────────────────────────────

fun ArticleEntity.toDomain(): Article = Article(
    url = url,
    sourceId = sourceId,
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    category = category,
    isBookmarked = isBookmarked
)

// ─── Domain → Entity ─────────────────────────────────────────────────────────

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    url = url,
    sourceId = sourceId,
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    category = category,
    isBookmarked = isBookmarked
)
