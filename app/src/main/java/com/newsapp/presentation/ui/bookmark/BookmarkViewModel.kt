package com.newsapp.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.GetBookmarksUseCase
import com.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookmarkUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    val uiState: StateFlow<BookmarkUiState> = getBookmarksUseCase()
        .map { articles -> BookmarkUiState(articles = articles, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BookmarkUiState()
        )

    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            toggleBookmarkUseCase(article.url, false)
        }
    }
}
