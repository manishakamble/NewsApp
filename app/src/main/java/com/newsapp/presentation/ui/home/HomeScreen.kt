package com.newsapp.presentation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newsapp.presentation.ui.components.*
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onArticleClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ─── Top Bar ─────────────────────────────────────────────────────────
        TopAppBar(
            title = {
                Text(
                    text = "📰 NewsDaily",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        // ─── Category Chips ───────────────────────────────────────────────────
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(viewModel.categories) { category ->
                val selected = uiState.selectedCategory == category
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.onCategorySelected(category) },
                    label = {
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    shape = RoundedCornerShape(50)
                )
            }
        }

        // ─── Content ──────────────────────────────────────────────────────────
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = viewModel::onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.isLoading && uiState.articles.isEmpty() -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(4) { ArticleShimmerCard() }
                    }
                }

                uiState.error != null && uiState.articles.isEmpty() -> {
                    ErrorView(
                        message = uiState.error!!,
                        onRetry = { viewModel.loadArticles(uiState.selectedCategory, true) }
                    )
                }

                uiState.articles.isEmpty() && !uiState.isLoading -> {
                    EmptyView(
                        emoji = "🗞️",
                        title = "No Articles",
                        subtitle = "Pull down to refresh"
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Featured first article (big card)
                        if (uiState.articles.isNotEmpty()) {
                            item {
                                ArticleCard(
                                    article = uiState.articles.first(),
                                    onClick = {
                                        onArticleClick(uiState.articles.first().url)
                                    },
                                    onBookmarkToggle = {
                                        viewModel.toggleBookmark(uiState.articles.first())
                                    }
                                )
                            }
                        }

                        // Remaining articles as compact rows
                        if (uiState.articles.size > 1) {
                            item {
                                Text(
                                    text = "Latest",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            items(
                                items = uiState.articles.drop(1),
                                key = { it.url }
                            ) { article ->
                                ArticleListItem(
                                    article = article,
                                    onClick = { onArticleClick(article.url) },
                                    onBookmarkToggle = { viewModel.toggleBookmark(article) }
                                )
                            }
                        }

                        // Offline notice

                        item {
                            // AnimatedVisibility(visible = uiState.error != null) {
                            Surface(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "⚡ Showing cached data. Pull to refresh when online.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                            // }
                        }

                        item { Spacer(Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}
