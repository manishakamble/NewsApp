package com.newsapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.model.Article
import com.newsapp.domain.usecase.SearchNewsUseCase
import com.newsapp.domain.usecase.ToggleBookmarkUseCase
import com.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasSearched: Boolean = false
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(500)
                .filter { it.length >= 2 }
                .distinctUntilChanged()
                .collect { query -> performSearch(query) }
        }
    }

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
        if (query.isEmpty()) {
            _uiState.update { it.copy(articles = emptyList(), hasSearched = false, error = null) }
        }
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchNewsUseCase(query).collect { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update {
                        it.copy(isLoading = true, hasSearched = true, error = null)
                    }
                    is Resource.Success -> _uiState.update {
                        it.copy(articles = result.data, isLoading = false)
                    }
                    is Resource.Error -> _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
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
}
