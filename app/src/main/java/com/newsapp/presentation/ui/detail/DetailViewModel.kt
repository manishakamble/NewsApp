package com.newsapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.GetArticleByUrlUseCase
import com.newsapp.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val article: Article? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadArticle(url: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val article = getArticleByUrlUseCase(url)
            if (article != null) {
                _uiState.update { it.copy(article = article, isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Article not found") }
            }
        }
    }

    fun toggleBookmark() {
        val article = _uiState.value.article ?: return
        viewModelScope.launch {
            val newState = !article.isBookmarked
            toggleBookmarkUseCase(article.url, newState)
            _uiState.update { it.copy(article = article.copy(isBookmarked = newState)) }
        }
    }
}
