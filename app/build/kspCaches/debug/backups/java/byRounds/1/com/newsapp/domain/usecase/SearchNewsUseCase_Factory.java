package com.newsapp.domain.usecase;

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
public final class SearchNewsUseCase_Factory implements Factory<SearchNewsUseCase> {
  private final Provider<NewsRepository> repositoryProvider;

  public SearchNewsUseCase_Factory(Provider<NewsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchNewsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SearchNewsUseCase_Factory create(Provider<NewsRepository> repositoryProvider) {
    return new SearchNewsUseCase_Factory(repositoryProvider);
  }

  public static SearchNewsUseCase newInstance(NewsRepository repository) {
    return new SearchNewsUseCase(repository);
  }
}
