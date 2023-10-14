package ru.astondevs.worldnews.domain.remote.usecase

import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.NewsApiRepository

class GetFilterNewsUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    suspend operator fun invoke(
        query: String?,
        sources: String?,
        pageSize: Int?,
        filter: String?,
        language: String?,
        startDate: String?,
        endDate: String?,
    ): NewsResponse = newsApiRepository.getFilterNews(query, sources, pageSize, filter, language, startDate, endDate)

}
