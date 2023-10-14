package ru.astondevs.worldnews.domain.remote.usecase

import ru.astondevs.worldnews.data.datasource.remote.response.SourcesList
import ru.astondevs.worldnews.domain.remote.NewsApiRepository

class GetSourcesUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    suspend operator fun invoke(): SourcesList = newsApiRepository.getSources()

}
