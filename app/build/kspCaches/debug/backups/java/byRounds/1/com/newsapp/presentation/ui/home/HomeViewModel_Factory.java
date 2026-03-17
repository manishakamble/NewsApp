package com.newsapp.presentation.ui.home;

import com.newsapp.domain.usecase.GetTopHeadlinesUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GetTopHeadlinesUseCase> getTopHeadlinesUseCaseProvider;

  private final Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider;

  public HomeViewModel_Factory(Provider<GetTopHeadlinesUseCase> getTopHeadlinesUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    this.getTopHeadlinesUseCaseProvider = getTopHeadlinesUseCaseProvider;
    this.toggleBookmarkUseCaseProvider = toggleBookmarkUseCaseProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(getTopHeadlinesUseCaseProvider.get(), toggleBookmarkUseCaseProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetTopHeadlinesUseCase> getTopHeadlinesUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    return new HomeViewModel_Factory(getTopHeadlinesUseCaseProvider, toggleBookmarkUseCaseProvider);
  }

  public static HomeViewModel newInstance(GetTopHeadlinesUseCase getTopHeadlinesUseCase,
      ToggleBookmarkUseCase toggleBookmarkUseCase) {
    return new HomeViewModel(getTopHeadlinesUseCase, toggleBookmarkUseCase);
  }
}
