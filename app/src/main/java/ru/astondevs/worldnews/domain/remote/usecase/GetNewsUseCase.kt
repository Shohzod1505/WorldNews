package ru.astondevs.worldnews.domain.remote.usecase

import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.NewsApiRepository

class GetNewsUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    suspend operator fun invoke(
        query: String?
    ): NewsResponse = newsApiRepository.getNews(query)

}
