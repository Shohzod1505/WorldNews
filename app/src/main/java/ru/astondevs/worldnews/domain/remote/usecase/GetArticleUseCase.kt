package ru.astondevs.worldnews.domain.remote.usecase

import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.NewsApiRepository

class GetArticleUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    suspend operator fun invoke(
        query: String?,
        pageSize: Int?,
    ): NewsResponse = newsApiRepository.getArticle(query, pageSize)

}
