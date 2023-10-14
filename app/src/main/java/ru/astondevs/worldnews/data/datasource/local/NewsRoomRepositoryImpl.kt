package ru.astondevs.worldnews.data.datasource.local

import ru.astondevs.worldnews.data.datasource.local.dao.NewsDao
import ru.astondevs.worldnews.domain.local.NewsRoomRepository

class NewsRoomRepositoryImpl(
    private val newsDao: NewsDao
): NewsRoomRepository {

//    override suspend fun saveArticleUseCase(article: Article) {
//        newsDao.save(article)
//    }
//
//    override suspend fun deleteArticleUseCase(article: Article) {
//        newsDao.delete(article)
//    }

}
