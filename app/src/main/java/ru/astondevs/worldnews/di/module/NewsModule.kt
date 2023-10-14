package ru.astondevs.worldnews.di.module

import dagger.Module
import dagger.Provides
import ru.astondevs.worldnews.data.datasource.NewsApiRepositoryImpl
import ru.astondevs.worldnews.data.datasource.local.NewsRoomRepositoryImpl
import ru.astondevs.worldnews.data.datasource.local.dao.NewsDao
import ru.astondevs.worldnews.data.datasource.remote.NewsApi
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetSourcesUseCase
import ru.astondevs.worldnews.domain.remote.NewsApiRepository
import ru.astondevs.worldnews.domain.local.NewsRoomRepository
import ru.astondevs.worldnews.domain.remote.usecase.GetArticleUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetFilterNewsUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsCategoryUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetNewsSourcesUseCase
import ru.astondevs.worldnews.domain.remote.usecase.GetRxJavaUseCase

@Module
class NewsModule {

    @Provides
    fun provideNewsApiRepository(
        newsApi: NewsApi
    ): NewsApiRepository = NewsApiRepositoryImpl(newsApi)

    @Provides
    fun provideNewsRoomRepository(
        newsDao: NewsDao
    ): NewsRoomRepository = NewsRoomRepositoryImpl(newsDao)

    @Provides
    fun provideNewsUseCase(
        newsApiRepository: NewsApiRepository
    ): GetNewsUseCase = GetNewsUseCase(newsApiRepository)

    @Provides
    fun provideNewsSourcesUseCase(
        newsApiRepository: NewsApiRepository
    ): GetNewsSourcesUseCase = GetNewsSourcesUseCase(newsApiRepository)

    @Provides
    fun provideFilterNewsUseCase(
        newsApiRepository: NewsApiRepository
    ): GetFilterNewsUseCase = GetFilterNewsUseCase(newsApiRepository)

    @Provides
    fun provideGetRxJavaUseCase(
        newsApiRepository: NewsApiRepository
    ): GetRxJavaUseCase = GetRxJavaUseCase(newsApiRepository)

    @Provides
    fun provideGetNewsCategoryUseCase(
        newsApiRepository: NewsApiRepository
    ): GetNewsCategoryUseCase = GetNewsCategoryUseCase(newsApiRepository)

    @Provides
    fun provideSourcesUseCase(
        newsApiRepository: NewsApiRepository
    ): GetSourcesUseCase = GetSourcesUseCase(newsApiRepository)

    @Provides
    fun provideArticleUseCase(
        newsApiRepository: NewsApiRepository
    ): GetArticleUseCase = GetArticleUseCase(newsApiRepository)

//    @Provides
//    fun provideSaveArticleUseCase(
//        newsRoomRepository: NewsRoomRepository
//    ): SaveArticleUseCase = SaveArticleUseCase(newsRoomRepository)
//
//    @Provides
//    fun provideDeleteArticleUseCase(
//        newsRoomRepository: NewsRoomRepository
//    ): DeleteArticleUseCase = DeleteArticleUseCase(newsRoomRepository)

}
