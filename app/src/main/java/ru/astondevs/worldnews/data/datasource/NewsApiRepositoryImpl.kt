package ru.astondevs.worldnews.data.datasource

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.astondevs.worldnews.data.datasource.remote.NewsApi
import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.data.datasource.remote.response.SourcesList
import ru.astondevs.worldnews.domain.remote.NewsApiRepository
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle

class NewsApiRepositoryImpl(
    private val newsApi: NewsApi
): NewsApiRepository {

    override suspend fun getNews(
        query: String?
    ): NewsResponse = newsApi.getNews(query)

    override suspend fun getNewsSources(
        sources: String?,
        pageSize: Int?,
    ): NewsResponse = newsApi.getNewsSources(sources, pageSize)

    override fun getRxJava(
        query: String?
    ): Single<List<NewsItem.Article>> = newsApi.getRxJava(query)
        .subscribeOn(Schedulers.io())
        .map{ it.articles?.toListAdapterArticle() ?: emptyList() }

    override fun getNewsCategory(
        category: String?
    ): Single<List<NewsItem.Article>> = newsApi.getRxJava(category)
        .subscribeOn(Schedulers.io())
        .map{ it.articles?.toListAdapterArticle() ?: emptyList() }

    override suspend fun getArticle(
        query: String?,
        pageSize: Int?
    ): NewsResponse = newsApi.getArticle(query, pageSize)

    override suspend fun getSources(): SourcesList = newsApi.getSources()

    override suspend fun getFilterNews(
        query: String?,
        sources: String?,
        pageSize: Int?,
        filter: String?,
        language: String?,
        startDate: String?,
        endDate: String?
    ): NewsResponse = newsApi.filterArticle(query, sources, pageSize, filter, language, startDate, endDate)

}
