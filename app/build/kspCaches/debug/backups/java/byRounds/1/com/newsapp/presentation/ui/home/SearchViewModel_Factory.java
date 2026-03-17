package com.newsapp.presentation.ui.home;

import com.newsapp.domain.usecase.SearchNewsUseCase;
import com.newsapp.domain.usecase.ToggleBookmarkUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<SearchNewsUseCase> searchNewsUseCaseProvider;

  private final Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider;

  public SearchViewModel_Factory(Provider<SearchNewsUseCase> searchNewsUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    this.searchNewsUseCaseProvider = searchNewsUseCaseProvider;
    this.toggleBookmarkUseCaseProvider = toggleBookmarkUseCaseProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(searchNewsUseCaseProvider.get(), toggleBookmarkUseCaseProvider.get());
  }

  public static SearchViewModel_Factory create(
      Provider<SearchNewsUseCase> searchNewsUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    return new SearchViewModel_Factory(searchNewsUseCaseProvider, toggleBookmarkUseCaseProvider);
  }

  public static SearchViewModel newInstance(SearchNewsUseCase searchNewsUseCase,
      ToggleBookmarkUseCase toggleBookmarkUseCase) {
    return new SearchViewModel(searchNewsUseCase, toggleBookmarkUseCase);
  }
}
