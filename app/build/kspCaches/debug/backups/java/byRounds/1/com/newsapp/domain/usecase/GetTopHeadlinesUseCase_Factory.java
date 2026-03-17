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
public final class GetTopHeadlinesUseCase_Factory implements Factory<GetTopHeadlinesUseCase> {
  private final Provider<NewsRepository> repositoryProvider;

  public GetTopHeadlinesUseCase_Factory(Provider<NewsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTopHeadlinesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTopHeadlinesUseCase_Factory create(Provider<NewsRepository> repositoryProvider) {
    return new GetTopHeadlinesUseCase_Factory(repositoryProvider);
  }

  public static GetTopHeadlinesUseCase newInstance(NewsRepository repository) {
    return new GetTopHeadlinesUseCase(repository);
  }
}
