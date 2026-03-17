package com.newsapp.data.repository;

import com.newsapp.data.local.dao.ArticleDao;
import com.newsapp.data.remote.api.NewsApiService;
import com.newsapp.util.NetworkUtils;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class NewsRepositoryImpl_Factory implements Factory<NewsRepositoryImpl> {
  private final Provider<NewsApiService> apiServiceProvider;

  private final Provider<ArticleDao> articleDaoProvider;

  private final Provider<NetworkUtils> networkUtilsProvider;

  public NewsRepositoryImpl_Factory(Provider<NewsApiService> apiServiceProvider,
      Provider<ArticleDao> articleDaoProvider, Provider<NetworkUtils> networkUtilsProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.articleDaoProvider = articleDaoProvider;
    this.networkUtilsProvider = networkUtilsProvider;
  }

  @Override
  public NewsRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), articleDaoProvider.get(), networkUtilsProvider.get());
  }

  public static NewsRepositoryImpl_Factory create(Provider<NewsApiService> apiServiceProvider,
      Provider<ArticleDao> articleDaoProvider, Provider<NetworkUtils> networkUtilsProvider) {
    return new NewsRepositoryImpl_Factory(apiServiceProvider, articleDaoProvider, networkUtilsProvider);
  }

  public static NewsRepositoryImpl newInstance(NewsApiService apiService, ArticleDao articleDao,
      NetworkUtils networkUtils) {
    return new NewsRepositoryImpl(apiService, articleDao, networkUtils);
  }
}
