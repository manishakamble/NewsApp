package com.newsapp.presentation.ui.bookmark;

import com.newsapp.domain.usecase.GetBookmarksUseCase;
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
public final class BookmarkViewModel_Factory implements Factory<BookmarkViewModel> {
  private final Provider<GetBookmarksUseCase> getBookmarksUseCaseProvider;

  private final Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider;

  public BookmarkViewModel_Factory(Provider<GetBookmarksUseCase> getBookmarksUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    this.getBookmarksUseCaseProvider = getBookmarksUseCaseProvider;
    this.toggleBookmarkUseCaseProvider = toggleBookmarkUseCaseProvider;
  }

  @Override
  public BookmarkViewModel get() {
    return newInstance(getBookmarksUseCaseProvider.get(), toggleBookmarkUseCaseProvider.get());
  }

  public static BookmarkViewModel_Factory create(
      Provider<GetBookmarksUseCase> getBookmarksUseCaseProvider,
      Provider<ToggleBookmarkUseCase> toggleBookmarkUseCaseProvider) {
    return new BookmarkViewModel_Factory(getBookmarksUseCaseProvider, toggleBookmarkUseCaseProvider);
  }

  public static BookmarkViewModel newInstance(GetBookmarksUseCase getBookmarksUseCase,
      ToggleBookmarkUseCase toggleBookmarkUseCase) {
    return new BookmarkViewModel(getBookmarksUseCase, toggleBookmarkUseCase);
  }
}
