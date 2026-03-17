package com.newsapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.model.CATEGORIES
import com.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.newsapp.domain.usecase.ToggleBookmarkUseCase
import com.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String = "general",
    val isRefreshing: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        loadArticles("general")
    }

    fun loadArticles(category: String, forceRefresh: Boolean = false) {
        fetchJob?.cancel()
        _uiState.update { it.copy(selectedCategory = category) }

        fetchJob = viewModelScope.launch {
            getTopHeadlinesUseCase(category, forceRefresh)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update {
                            it.copy(isLoading = it.articles.isEmpty(), isRefreshing = forceRefresh)
                        }
                        is Resource.Success -> _uiState.update {
                            it.copy(
                                articles = result.data,
                                isLoading = false,
                                isRefreshing = false,
                                error = null
                            )
                        }
                        is Resource.Error -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = if (it.articles.isEmpty()) result.message else null
                            )
                        }
                    }
                }
        }
    }

    fun onCategorySelected(category: String) {
        if (category != _uiState.value.selectedCategory) {
            loadArticles(category)
        }
    }

    fun onRefresh() {
        loadArticles(_uiState.value.selectedCategory, forceRefresh = true)
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            val newState = !article.isBookmarked
            toggleBookmarkUseCase(article.url, newState)
            _uiState.update { state ->
                state.copy(
                    articles = state.articles.map {
                        if (it.url == article.url) it.copy(isBookmarked = newState) else it
                    }
                )
            }
        }
    }

    val categories: List<String> = CATEGORIES
}
