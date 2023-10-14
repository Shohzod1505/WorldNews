package ru.astondevs.worldnews.domain.remote.usecase

import io.reactivex.rxjava3.core.Single
import ru.astondevs.worldnews.data.datasource.remote.response.NewsResponse
import ru.astondevs.worldnews.domain.remote.NewsApiRepository
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem

class GetNewsCategoryUseCase(
    private val newsApiRepository: NewsApiRepository
) {

    operator fun invoke(
        category: String?
    ): Single<List<NewsItem.Article>> = newsApiRepository.getNewsCategory(category)

}
