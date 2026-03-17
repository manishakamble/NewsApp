package com.newsapp.presentation.ui.detail;

import com.newsapp.domain.usecase.GetArticleByUrlUseCase;
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
public final class DetailViewModel_Factory implements Factory<DetailViewModel> {
  private final Provider<GetArticleByUrlUseCase> getArticleByUrlUseCaseProvider;

  private final Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider;

  public DetailViewModel_Factory(Provider<GetArticleByUrlUseCase> getArticleByUrlUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    this.getArticleByUrlUseCaseProvider = getArticleByUrlUseCaseProvider;
    this.toggleBookmarkUseCaseProvider = toggleBookmarkUseCaseProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(getArticleByUrlUseCaseProvider.get(), toggleBookmarkUseCaseProvider.get());
  }

  public static DetailViewModel_Factory create(
      Provider<GetArticleByUrlUseCase> getArticleByUrlUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    return new DetailViewModel_Factory(getArticleByUrlUseCaseProvider, toggleBookmarkUseCaseProvider);
  }

  public static DetailViewModel newInstance(GetArticleByUrlUseCase getArticleByUrlUseCase,
      ToggleBookmarkUseCase toggleBookmarkUseCase) {
    return new DetailViewModel(getArticleByUrlUseCase, toggleBookmarkUseCase);
  }
}
