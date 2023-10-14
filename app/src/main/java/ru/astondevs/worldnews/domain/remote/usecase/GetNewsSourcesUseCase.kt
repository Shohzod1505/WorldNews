package ru.astondevs.worldnews.domain.remote.usecase

import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.NewsApiRepository

class GetNewsSourcesUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    suspend operator fun invoke(
        sources: String?,
        pageSize: Int?,
    ): NewsResponse = newsApiRepository.getNewsSources(sources, pageSize)

}
