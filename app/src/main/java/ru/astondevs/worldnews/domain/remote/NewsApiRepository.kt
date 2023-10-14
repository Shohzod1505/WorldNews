package ru.astondevs.worldnews.domain.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query
import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.data.datasource.remote.response.SourcesList
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem

interface NewsApiRepository {

    suspend fun getNews(
        query: String?
    ): NewsResponse

    suspend fun getNewsSources(
        sources: String?,
        pageSize: Int?,
    ): NewsResponse

    fun getRxJava(
        query: String?
    ): Single<List<NewsItem.Article>>

    fun getNewsCategory(
        category: String?
    ): Single<List<NewsItem.Article>>

    suspend fun getArticle(
        query: String?,
        pageSize: Int?,
    ): NewsResponse

    suspend fun getSources(): SourcesList

    suspend fun getFilterNews(
        query: String?,
        sources: String?,
        pageSize: Int?,
        filter: String?,
        language: String?,
        startDate: String?,
        endDate: String?,
    ): NewsResponse

}
