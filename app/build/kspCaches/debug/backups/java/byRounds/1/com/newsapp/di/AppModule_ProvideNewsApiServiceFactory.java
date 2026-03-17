package com.newsapp.di;

import com.newsapp.data.remote.api.NewsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class AppModule_ProvideNewsApiServiceFactory implements Factory<NewsApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideNewsApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public NewsApiService get() {
    return provideNewsApiService(retrofitProvider.get());
  }

  public static AppModule_ProvideNewsApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideNewsApiServiceFactory(retrofitProvider);
  }

  public static NewsApiService provideNewsApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNewsApiService(retrofit));
  }
}
