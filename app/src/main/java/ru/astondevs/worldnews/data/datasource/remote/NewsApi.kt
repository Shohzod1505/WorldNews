package ru.astondevs.worldnews.data.datasource.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.data.datasource.remote.response.SourcesList

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String?
    ): NewsResponse

    @GET("everything")
    suspend fun getNewsSources(
        @Query("sources") sources: String?,
        @Query("pageSize") pageSize: Int?,
    ): NewsResponse

    @GET("everything")
    fun getRxJava(
        @Query("q") query: String?
    ): Single<NewsResponse>

    @GET("top-headlines")
    fun getNewsCategory(
        @Query("category") category: String?
    ): Single<NewsResponse>

    @GET("top-headlines/sources")
    suspend fun getSources(): SourcesList

    @GET("everything")
    suspend fun filterArticle(
        @Query("q") query: String?,
        @Query("sources") sources: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("sortBy") filter: String?,
        @Query("language") language: String?,
        @Query("from") startDate: String?,
        @Query("to") endDate: String?,
    ): NewsResponse

    @GET("everything")
    suspend fun getArticle(
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int?,
    ): NewsResponse

}
