package ru.astondevs.worldnews.domain.local.usecase

import ru.astondevs.worldnews.domain.local.NewsRoomRepository

class DeleteArticleUseCase(
    private val newsRoomRepository: NewsRoomRepository
) {
//    suspend operator fun invoke(
//        article: Article
//    ) = newsRoomRepository.deleteArticleUseCase(article)
}
