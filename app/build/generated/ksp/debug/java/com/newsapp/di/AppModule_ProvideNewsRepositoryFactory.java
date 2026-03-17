package com.newsapp.di;

import com.newsapp.data.local.dao.ArticleDao;
import com.newsapp.data.remote.api.NewsApiService;
import com.newsapp.domain.usecase.NewsRepository;
import com.newsapp.util.NetworkUtils;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideNewsRepositoryFactory implements Factory<NewsRepository> {
  private final Provider<NewsApiService> apiServiceProvider;

  private final Provider<ArticleDao> articleDaoProvider;

  private final Provider<NetworkUtils> networkUtilsProvider;

  public AppModule_ProvideNewsRepositoryFactory(Provider<NewsApiService> apiServiceProvider,
      Provider<ArticleDao> articleDaoProvider, Provider<NetworkUtils> networkUtilsProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.articleDaoProvider = articleDaoProvider;
    this.networkUtilsProvider = networkUtilsProvider;
  }

  @Override
  public NewsRepository get() {
    return provideNewsRepository(apiServiceProvider.get(), articleDaoProvider.get(), networkUtilsProvider.get());
  }

  public static AppModule_ProvideNewsRepositoryFactory create(
      Provider<NewsApiService> apiServiceProvider, Provider<ArticleDao> articleDaoProvider,
      Provider<NetworkUtils> networkUtilsProvider) {
    return new AppModule_ProvideNewsRepositoryFactory(apiServiceProvider, articleDaoProvider, networkUtilsProvider);
  }

  public static NewsRepository provideNewsRepository(NewsApiService apiService,
      ArticleDao articleDao, NetworkUtils networkUtils) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNewsRepository(apiService, articleDao, networkUtils));
  }
}
